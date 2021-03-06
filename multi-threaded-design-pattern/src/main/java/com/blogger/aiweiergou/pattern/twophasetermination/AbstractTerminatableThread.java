package com.blogger.aiweiergou.pattern.twophasetermination;

/**
 * Created by sunyinjie on 2017/9/29.
 */
public abstract class AbstractTerminatableThread extends Thread implements Terminatable {
    public final TerminationToken terminationToken;

    public AbstractTerminatableThread() {
        this(new TerminationToken());
    }

    public AbstractTerminatableThread(TerminationToken terminationToken) {
        super();
        this.terminationToken = terminationToken;
        terminationToken.register(this);
    }

    protected abstract void doRun() throws Exception;

    protected void doCleanup(Exception e) {
        //do nothing
    }

    protected void doTerminiate() {
        //do nothing
    }

    @Override
    public void run() {
        Exception ex = null;
        try {
            for (; ; ) {
                if (terminationToken.isToShutdown() && terminationToken.reservations.get() <= 0) {
                    break;
                }
                doRun();
            }
        } catch (Exception e) {
            ex = e;
        } finally {
            try {
                doCleanup(ex);
            } catch (Exception e) {
                terminationToken.notifyThreadTermination(this);
            }
        }
    }

    @Override
    public void interrupt() {
        terminate();
    }

    public void terminate() {
        terminationToken.setToShutdown(true);
        try {
            doTerminiate();
        } finally {
            //如果没有等待的任务，尝试强制终止线程
            if (terminationToken.reservations.get() <= 0) {
                super.interrupt();
            }
        }
    }

    public void terminate(boolean waitUtilThreadTerminated) {
        terminate();
        if (waitUtilThreadTerminated) {
            try {
                this.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
