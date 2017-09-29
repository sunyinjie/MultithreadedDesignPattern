package com.blogger.aiweiergou.twophasetermination;

import com.blogger.aiweiergou.common.beans.AlarmInfo;
import com.blogger.aiweiergou.common.enums.AlarmType;
import com.blogger.aiweiergou.guardedsuspension.AlarmAgent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sunyinjie on 2017/9/29.
 */
public class AlarmSendingThread extends AbstractTerminatableThread {
    private final AlarmAgent alarmAgent = new AlarmAgent();
    private final BlockingQueue<AlarmInfo> alarmQueue;
    private final ConcurrentHashMap<String, AtomicInteger> submittedAlarmRegistry;

    public AlarmSendingThread() {
        this.alarmQueue = new ArrayBlockingQueue<AlarmInfo>(100);
        this.submittedAlarmRegistry = new ConcurrentHashMap<String, AtomicInteger>();
        alarmAgent.init();
    }

    public int sendAlarm(AlarmInfo alarmInfo) {
        AlarmType type = alarmInfo.getType();
        String id = alarmInfo.getId();
        String extraInfo = alarmInfo.getExtraInfo();
        if (terminationToken.isToShutdown()) {
            System.out.println("rejected alarm:" + id + "," + extraInfo);
            return -1;
        }
        int duplicateSubmissionCnt = 0;
        try {
            AtomicInteger prevSubmittedCounter;
            prevSubmittedCounter = submittedAlarmRegistry.putIfAbsent(type.toString() + ':' + id + '@' + extraInfo, new AtomicInteger(0));
            if (null == prevSubmittedCounter) {
                terminationToken.reservations.incrementAndGet();
                alarmQueue.put(alarmInfo);
            } else {
                duplicateSubmissionCnt = prevSubmittedCounter.incrementAndGet();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return duplicateSubmissionCnt;
    }

    @Override
    protected void doCleanup(Exception e) {
        if (null != e && !(e instanceof InterruptedException)) {
            e.printStackTrace();
        }
        alarmAgent.disconnect();
    }

    @Override
    protected void doRun() throws Exception {
        AlarmInfo alarmInfo;
        alarmInfo = alarmQueue.take();
        terminationToken.reservations.decrementAndGet();
        try {
            alarmAgent.sendAlarm(alarmInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AlarmType.RESUME == alarmInfo.getType()) {
            String key = AlarmType.FAULT.toString() + ':' + alarmInfo.getId() + '@' + alarmInfo.getExtraInfo();
            submittedAlarmRegistry.remove(key);
            key = AlarmType.RESUME.toString() + ':' + alarmInfo.getId() + '@' + alarmInfo.getExtraInfo();
            submittedAlarmRegistry.remove(key);
        }
    }
}
