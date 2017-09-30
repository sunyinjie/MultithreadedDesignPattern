package com.blogger.aiweiergou.twophasetermination;


import com.blogger.aiweiergou.common.enums.AlarmType;

/**
 * 两阶段终止模式
 * Created by sunyinjie on 2017/9/29.
 */
public class Test {
    public static void main(String[] args) {
        AlarmMgr alarmMgr = AlarmMgr.getInstance();

        for (int i = 0; i < 100; i++) {
            alarmMgr.sendAlarm(AlarmType.RESUME, String.valueOf(i), "exexex------" + i + "---balabalbala");
        }


        alarmMgr.init();

        alarmMgr.shutdown();

    }
}
