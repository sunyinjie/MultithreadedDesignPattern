package com.blogger.aiweiergou.producerconsumer.api;

/**
 * Created by sunyinjie on 2017/9/30.
 */
public interface Channel<P> {
    P take() throws InterruptedException;

    void put(P product) throws InterruptedException;
}
