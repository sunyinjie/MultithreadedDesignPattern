package com.blogger.aiweiergou.immutableobject;

/**
 * Created by sunyinjie on 2017/9/23.
 */
public final class MMSCInfo {
    private final String id;

    public MMSCInfo(String id) {
        this.id = id;
    }

    public MMSCInfo(MMSCInfo prototype) {
        this.id = prototype.getId();
    }

    public String getId() {
        return id;
    }
}
