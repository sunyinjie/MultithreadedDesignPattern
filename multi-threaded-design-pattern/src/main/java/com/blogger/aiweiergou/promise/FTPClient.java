package com.blogger.aiweiergou.promise;

import com.blogger.aiweiergou.common.enums.FTP;

import java.io.InputStream;
import java.util.Random;

/**
 * Created by sunyinjie on 2017/9/30.
 */
public class FTPClient {
    public void configure(FTPClientConfig config) {
        System.out.println("设置ftp连接信息");
    }

    public void connect(String ftpServer) {
        System.out.println("连接到ftp");
    }

    public String getReplyString() {
        return new Random().toString();
    }

    public int getReplyCode() {
        return new Random().nextInt(100);
    }

    public void disconnect() {
        System.out.println("断开ftp");
    }

    public boolean login(String userName, String password) {
        return true;
    }

    public int cwd(String s) {
        return new Random().nextInt(5);
    }

    public void setFileType(FTP asciiFileType) {
        System.out.println("设置文件类型");
    }

    public void makeDirectory(String dirName) {
        System.out.println("创建目录");
    }

    public boolean storeFile(String fileName, InputStream dataIn) {
        System.out.println("上传文件...");
        return true;
    }

    public boolean isConnected() {
        return true;
    }
}
