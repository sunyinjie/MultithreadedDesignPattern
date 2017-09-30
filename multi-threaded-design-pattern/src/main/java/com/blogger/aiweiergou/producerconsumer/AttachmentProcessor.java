package com.blogger.aiweiergou.producerconsumer;

import com.blogger.aiweiergou.producerconsumer.api.Channel;
import com.blogger.aiweiergou.producerconsumer.queue.BlockingQueueChannel;
import com.blogger.aiweiergou.twophasetermination.AbstractTerminatableThread;

import java.io.File;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by sunyinjie on 2017/9/30.
 */
public class AttachmentProcessor {
    private final String ATTACHMENT_STORE_BASE_DIR = "C:\\Users\\sunyinjie\\Desktop";

    private final Channel<File> channel = new BlockingQueueChannel<File>(new ArrayBlockingQueue<File>(200));

    private final AbstractTerminatableThread indexingThread = new AbstractTerminatableThread() {
        @Override
        protected void doRun() throws Exception {
            File file = null;
            file = channel.take();
            try {
                indexFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                terminationToken.reservations.decrementAndGet();
            }
        }

        private void indexFile(File file) {
            //索引逻辑
            Random rnd = new Random();
            try {
                Thread.sleep(rnd.nextInt(100));
                System.out.println(file.getName() + "索引计算完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void init() {
        indexingThread.start();
    }

    public void shutdown() {
        indexingThread.terminate();
    }

    public void saveAttachment(InputStream in, String docId, String orgFileName) {
        File file = saveAsFile(in, docId, orgFileName);
        try {
            channel.put(file);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        indexingThread.terminationToken.reservations.incrementAndGet();
    }

    private File saveAsFile(InputStream in, String docId, String orgFileName) {
        try {
            Thread.sleep(new Random().nextInt(100));
            System.out.println("保存文件成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new File("C:\\Users\\sunyinjie\\Desktop\\质检设置-初始化脚本.sql");
    }


}
