package com.blogger.aiweiergou.producerconsumer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sunyinjie on 2017/9/30.
 */
public class Test {
    public static void main(String[] args) {
        AttachmentProcessor producer = new AttachmentProcessor();
        producer.saveAttachment(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        }, "testDocSave", "testFileName");
        producer.init();
        producer.shutdown();
    }
}
