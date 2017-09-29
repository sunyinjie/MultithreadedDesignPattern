package com.blogger.aiweiergou.guardedsuspension;

import com.blogger.aiweiergou.common.beans.AlarmInfo;
import com.blogger.aiweiergou.guardedsuspension.api.Blocker;
import com.blogger.aiweiergou.guardedsuspension.api.GuardedAction;
import com.blogger.aiweiergou.guardedsuspension.api.Predicate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/**
 * Created by sunyinjie on 2017/9/29.
 */
public class AlarmAgent {
    private volatile boolean connectedToServer = false;

    private final Predicate agentConnected = new Predicate() {
        public boolean evaluate() {
            return connectedToServer;
        }
    };

    private final Blocker blocker = new ConditionVarBlocker();

    private final Timer heartbeatTimer = new Timer(true);

    public void sendAlarm(final AlarmInfo alarm) throws Exception {
        GuardedAction<Void> guardedAction = new GuardedAction<Void>(agentConnected) {
            public Void call() throws Exception {
                doSendAlarm(alarm);
                return null;
            }
        };
        blocker.callWithGuard(guardedAction);
    }

    private void doSendAlarm(AlarmInfo alarm) {
        System.out.println("发送警报:" + alarm);
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        Thread connectionThread = new Thread(new ConnectingTask());
        connectionThread.start();
        heartbeatTimer.schedule(new HeartbeatTask(), 60000, 2000);
    }

    public void disconnect() {
        System.out.println("与警报服务器断开连接");
        connectedToServer = false;
    }

    protected void onConnected() {
        try {
            blocker.signalAfter(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    connectedToServer = true;
                    System.out.println("连接到警报服务器");
                    return Boolean.TRUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDisconnected() {
        connectedToServer = false;
    }

    private class ConnectingTask implements Runnable {

        public void run() {
            //模拟连接操作
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onConnected();
        }
    }

    private class HeartbeatTask extends TimerTask {

        @Override
        public void run() {
            if (!testConnection()) {
                onDisconnected();
                reconnect();
            }
        }

        private void reconnect() {
            ConnectingTask connectingThread = new ConnectingTask();
            connectingThread.run();
        }

        private boolean testConnection() {
            return true;
        }
    }
}
