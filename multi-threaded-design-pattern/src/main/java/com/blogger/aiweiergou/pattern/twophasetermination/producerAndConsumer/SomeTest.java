package com.blogger.aiweiergou.pattern.twophasetermination.producerAndConsumer;


import com.blogger.aiweiergou.pattern.twophasetermination.AbstractTerminatableThread;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者消费者模式借助两阶段终止模式结束线程任务
 * Created by sunyinjie on 2017/9/30.
 */
public class SomeTest {
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(100);
    private final Producer producer = new Producer();
    private final Consumer consumer = new Consumer();

    private class Producer extends AbstractTerminatableThread {
        int i = 0;

        @Override
        protected void doRun() throws Exception {
            queue.put(String.valueOf(i++));
            consumer.terminationToken.reservations.incrementAndGet();
        }
    }

    private class Consumer extends AbstractTerminatableThread {

        @Override
        protected void doRun() throws Exception {
            String product = queue.take();
            System.out.println("Processing product:" + product);
            //do something
            try {
                Thread.sleep(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                terminationToken.reservations.decrementAndGet();
            }

        }
    }

    public void shutdown() {
        producer.terminate(true);
        consumer.terminate();
    }

    public void init() {
        producer.start();
        consumer.start();
    }

    public static void main(String[] args) throws InterruptedException {
        SomeTest test = new SomeTest();
        test.init();
        Thread.sleep(500);
        test.shutdown();
    }
}
