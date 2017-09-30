package com.blogger.aiweiergou.promise;

import com.blogger.aiweiergou.common.enums.FTP;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by sunyinjie on 2017/9/30.
 */
public class FTPClientUtil {
    private final FTPClient ftp = new FTPClient();

    private final Map<String, Boolean> dirCreateMap = new HashMap<String, Boolean>();

    private volatile static ThreadPoolExecutor threadPoolExecutor;

    private FTPClientUtil() {

    }

    static {
        threadPoolExecutor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());
    }


    public static Future<FTPClientUtil> newInstance(final String ftpServer, final String userName, final String password) {
        Callable<FTPClientUtil> callable = new Callable<FTPClientUtil>(){

            @Override
            public FTPClientUtil call() throws Exception {
                FTPClientUtil self = new FTPClientUtil();
                self.init(ftpServer, userName, password);
                return self;
            }
        };
        FutureTask<FTPClientUtil> task = new FutureTask<FTPClientUtil>(callable);
        threadPoolExecutor.execute(task);
        return task;
    }

    private void init(String ftpServer, String userName, String password) {
        FTPClientConfig config = new FTPClientConfig();
        ftp.configure(config);

        int reply;
        ftp.connect(ftpServer);

        System.out.println(ftp.getReplyString());

        reply = ftp.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new RuntimeException("FTP server refused connection");
        }
        boolean isOk = ftp.login(userName, password);
        if (isOk) {
            System.out.println(ftp.getReplyString());
        } else {
            throw new RuntimeException("Failed to login." + ftp.getReplyString());
        }

        reply = ftp.cwd("~/subspsync");

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new RuntimeException("Failed to change working directory.reply:" + reply);
        } else {
            System.out.println(ftp.getReplyString());
        }

        ftp.setFileType(FTP.ASCII_FILE_TYPE);
    }

    public void upload(File file) throws FileNotFoundException {
        InputStream dataIn = new BufferedInputStream(new FileInputStream(file), 1024 * 8);
        boolean isOk;
        String dirName = file.getParentFile().getName();
        String fileName = dirName + '/' + file.getName();
        ByteArrayInputStream checkFileInputStream = new ByteArrayInputStream("".getBytes());
        try {
            if (!dirCreateMap.containsKey(dirName)) {
                ftp.makeDirectory(dirName);
                dirCreateMap.put(dirName, null);
            }

            isOk = ftp.storeFile(fileName, dataIn);
            if (isOk) {
                ftp.storeFile(fileName + ".c", checkFileInputStream);
            } else {
                throw new RuntimeException("Failed to upload " + file + ",reply:" + ftp.getReplyString());
            }
        } finally {
            try {
                dataIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
