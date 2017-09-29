package com.blogger.aiweiergou.twophasetermination;

import com.blogger.aiweiergou.common.beans.AlarmInfo;
import com.blogger.aiweiergou.common.enums.AlarmType;

/**
 * Created by sunyinjie on 2017/9/29.
 */
public class AlarmMgr {
    private static final AlarmMgr INSTANCE = new AlarmMgr();

    private volatile boolean shutdownRequested = false;

    private final AlarmSendingThread alarmSendingThread;

    private AlarmMgr() {
        alarmSendingThread = new AlarmSendingThread();
    }

    public static AlarmMgr getInstance() {
        return INSTANCE;
    }

    public int sendAlarm(AlarmType type, String id, String extraInfo) {
        System.out.println("Trigger alarm " + type + "," + id + ',' + extraInfo);
        int duplicateSubmissionCnt = 0;
        try {
            AlarmInfo alarmInfo = new AlarmInfo(id, type);
            alarmInfo.setExtraInfo(extraInfo);
            duplicateSubmissionCnt = alarmSendingThread.sendAlarm(alarmInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duplicateSubmissionCnt;
    }

    public void init() {
        alarmSendingThread.start();
    }

    public synchronized void shutdown() {
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }
        alarmSendingThread.terminate();
        shutdownRequested = true;
    }
}
