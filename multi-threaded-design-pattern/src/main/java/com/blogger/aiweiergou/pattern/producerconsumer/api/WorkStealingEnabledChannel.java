package com.blogger.aiweiergou.pattern.producerconsumer.api;

import java.util.concurrent.BlockingDeque;

/**
 * Created by sunyinjie on 2017/9/30.
 */
public interface WorkStealingEnabledChannel<P> extends Channel<P> {
    P take(BlockingDeque<P> preferredQueue) throws InterruptedException;
}
