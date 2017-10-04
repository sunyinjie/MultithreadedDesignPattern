package com.blogger.aiweiergou.pattern.guardedsuspension.api;

import java.util.concurrent.Callable;

/**
 * Created by sunyinjie on 2017/9/29.
 */
public abstract class GuardedAction<V> implements Callable<V> {
    protected final Predicate guard;

    public GuardedAction(Predicate guard) {
        this.guard = guard;
    }

    public Predicate getGuard() {
        return guard;
    }
}
