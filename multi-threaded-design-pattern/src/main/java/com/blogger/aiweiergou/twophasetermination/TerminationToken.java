package com.blogger.aiweiergou.twophasetermination;


import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sunyinjie on 2017/9/29.
 */
public class TerminationToken {
    protected volatile boolean toShutdown = false;
    public final AtomicInteger reservations = new AtomicInteger(0);

    private final Queue<WeakReference<Terminatable>> coordinatedThreads;

    public TerminationToken() {
        coordinatedThreads = new ConcurrentLinkedDeque<WeakReference<Terminatable>>();
    }

    public void setToShutdown(boolean toShutdown) {
        this.toShutdown = toShutdown;
    }

    public boolean isToShutdown() {
        return toShutdown;
    }

    public void register(AbstractTerminatableThread t) {
        coordinatedThreads.add(new WeakReference<Terminatable>(t));
    }

    public void notifyThreadTermination(AbstractTerminatableThread t) {
        WeakReference<Terminatable> wrThread;
        Terminatable otherThread;
        while (null != (wrThread = coordinatedThreads.poll())) {
            otherThread = wrThread.get();
            if (null != otherThread && otherThread != t) {
                otherThread.terminate();
            }
        }
    }
}
