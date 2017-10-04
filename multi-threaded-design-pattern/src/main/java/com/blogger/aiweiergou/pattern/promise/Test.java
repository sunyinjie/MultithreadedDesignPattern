package com.blogger.aiweiergou.pattern.promise;

import java.util.HashMap;
import java.util.Map;

/**
 * 承诺者模式
 * java实现 future
 * Created by sunyinjie on 2017/9/30.
 */
public class Test {
    public static void main(String[] args) {
        Map<String, String> taskParameters = new HashMap<String, String>();
        taskParameters.put("server", "127.0.0.1");
        taskParameters.put("userName", "sunyinjie");
        taskParameters.put("password", "123456");
        Thread thread = new Thread(new DataSyncTask(taskParameters));
        thread.start();
    }
}
