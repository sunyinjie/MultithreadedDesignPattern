package com.blogger.aiweiergou.pattern.producerconsumer.queue;

import com.blogger.aiweiergou.pattern.producerconsumer.api.Channel;

import java.util.concurrent.BlockingQueue;

/**
 * Created by sunyinjie on 2017/9/30.
 */
public class BlockingQueueChannel<P> implements Channel<P> {
    private final BlockingQueue<P> queue;

    public BlockingQueueChannel(BlockingQueue<P> queue) {
        this.queue = queue;
    }

    @Override
    public P take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void put(P product) throws InterruptedException {
        queue.put(product);
    }
}
