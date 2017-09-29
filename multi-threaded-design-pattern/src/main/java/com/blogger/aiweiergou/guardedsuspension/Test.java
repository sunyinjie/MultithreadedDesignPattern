package com.blogger.aiweiergou.guardedsuspension;

import com.blogger.aiweiergou.common.beans.AlarmInfo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 保护性暂挂模式
 * Created by sunyinjie on 2017/9/29.
 */
public class Test {
    public static void main(String[] args) {
        final AlarmAgent aa = new AlarmAgent();
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    aa.sendAlarm(new AlarmInfo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                aa.disconnect();
            }
        }, 50, 10);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                aa.onConnected();
            }
        }, 50, 10);
    }
}
