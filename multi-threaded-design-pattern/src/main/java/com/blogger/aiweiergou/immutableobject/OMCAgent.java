package com.blogger.aiweiergou.immutableobject;

/**
 * Created by sunyinjie on 2017/9/23.
 */
public class OMCAgent extends Thread {
    @Override
    public void run() {
        boolean isTableModificationMsg = false;
        String updatedTableName = null;
        while (true) {
            //...
            /**
             * 从与omc连接的socket中读取消息并进行解析
             * 解析到数据表更新消息后，重置mmscrouter实例
             */
            if (isTableModificationMsg) {
                if ("MMSCInfo".equals(updatedTableName)) {
                    MMSCRouter.setInstance(new MMSCRouter());
                }
            }
            //...
        }
    }
}
