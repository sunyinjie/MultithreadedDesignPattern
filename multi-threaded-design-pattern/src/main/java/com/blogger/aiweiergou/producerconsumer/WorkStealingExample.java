package com.blogger.aiweiergou.producerconsumer;

import com.blogger.aiweiergou.producerconsumer.api.WorkStealingEnabledChannel;
import com.blogger.aiweiergou.producerconsumer.queue.WorkStealingChannel;
import com.blogger.aiweiergou.twophasetermination.AbstractTerminatableThread;
import com.blogger.aiweiergou.twophasetermination.TerminationToken;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by sunyinjie on 2017/9/30.
 */
public class WorkStealingExample {
    private final WorkStealingEnabledChannel<String> channel;
    private final TerminationToken token = new TerminationToken();

    public static void main(String[] args) throws InterruptedException {
        WorkStealingExample example = new WorkStealingExample();
        example.doSomething();
        Thread.sleep(4000L);
    }

    public WorkStealingExample() {
        int nCPU = Runtime.getRuntime().availableProcessors();
        int consumerCnt = nCPU / 2 + 1;
        BlockingDeque<String>[] managedQueues = new LinkedBlockingDeque[consumerCnt];
        channel = new WorkStealingChannel<String>(managedQueues);
        //该通道实例对应了多个队列实例managedQueues
        Consumer[] consumers = new Consumer[consumerCnt];
        for (int i = 0; i < consumerCnt; i++) {
            managedQueues[i] = new LinkedBlockingDeque<String>();
            consumers[i] = new Consumer(token, managedQueues[i]);
        }

        for (int i = 0; i < nCPU; i++) {
            new Producer().start();
        }

        for (int i = 0; i < consumerCnt; i++) {
            consumers[i].start();
        }
    }

    public void doSomething() {

    }

    private class Consumer extends AbstractTerminatableThread {
        private final BlockingDeque<String> workQueue;

        public Consumer(TerminationToken terminationToken, BlockingDeque<String> workQueue) {
            super(terminationToken);
            this.workQueue = workQueue;
        }

        @Override
        protected void doRun() throws Exception {
            String product = channel.take(workQueue);
            System.out.println("Processing product:" + product);

            try {
                Thread.sleep(new Random().nextInt(50));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                token.reservations.decrementAndGet();
            }
        }
    }

    private class Producer extends AbstractTerminatableThread {
        private int i = 0;

        @Override
        protected void doRun() throws Exception {
            channel.put(String.valueOf(i++));
            token.reservations.incrementAndGet();
        }
    }
}
