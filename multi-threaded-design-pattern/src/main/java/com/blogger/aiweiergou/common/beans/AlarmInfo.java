package com.blogger.aiweiergou.common.beans;

import com.blogger.aiweiergou.common.enums.AlarmType;

import java.util.Random;

/**
 * Created by sunyinjie on 2017/9/29.
 */
public class AlarmInfo {
    private String info = String.valueOf(new Random().nextInt());

    private String id;
    private AlarmType type;
    private String extraInfo;

    public AlarmInfo(String info) {
        this.info = info;
    }

    public AlarmInfo() {
    }

    public AlarmInfo(String id, AlarmType type) {
        this.id = id;
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AlarmType getType() {
        return type;
    }

    public void setType(AlarmType type) {
        this.type = type;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public String toString() {
        return "{\"AlarmInfo\":{"
                + "\"info\":\"" + info + "\""
                + ",\"id\":\"" + id + "\""
                + ",\"type\":\"" + type + "\""
                + ",\"extraInfo\":\"" + extraInfo + "\""
                + "}}";
    }
}
