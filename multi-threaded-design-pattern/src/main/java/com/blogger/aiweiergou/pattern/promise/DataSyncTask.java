package com.blogger.aiweiergou.pattern.promise;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by sunyinjie on 2017/9/30.
 */
public class DataSyncTask implements Runnable {
    private final Map<String, String> taskParameters;

    public DataSyncTask(Map<String, String> taskParameters) {
        this.taskParameters = taskParameters;
    }

    public void run() {
        String server = taskParameters.get("server");
        String userName = taskParameters.get("userName");
        String password = taskParameters.get("password");

        Future<FTPClientUtil> ftpClientUtilPromise = FTPClientUtil.newInstance(server, userName, password);

        FTPClientUtil ftpClientUtil = null;
        try {
            ftpClientUtil = ftpClientUtilPromise.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        uploadFiles(ftpClientUtil);
    }

    private void uploadFiles(FTPClientUtil ftpClientUtil) {
        Set<File> files = retrieveGeneratedFiles();
        for (File file : files) {
            try {
                ftpClientUtil.upload(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private Set<File> retrieveGeneratedFiles() {
        Set<File> files = new HashSet<File>();
        files.add(new File("E:\\projects\\jcode\\jtalk\\jtalk_bms\\base\\pom.xml"));
        files.add(new File("E:\\projects\\jcode\\jtalk\\jtalk_bms\\base\\build.sh"));
        return files;
    }


}
