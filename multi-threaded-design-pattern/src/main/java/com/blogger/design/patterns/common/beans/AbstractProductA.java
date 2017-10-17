package com.blogger.design.patterns.common.beans;

/**
 * Created by sunyinjie on 2017/10/17.
 */
public abstract class AbstractProductA {
    public abstract void diff();
    public void same() {
        System.out.println("productA公共的方法块");
        System.out.println("name=" + this.getName());
    }


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{\"Product\":{"
                + "\"name\":\"" + name + "\""
                + "}}";
    }
}
