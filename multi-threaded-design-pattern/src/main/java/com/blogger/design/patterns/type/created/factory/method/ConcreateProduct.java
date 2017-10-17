package com.blogger.design.patterns.type.created.factory.method;

import com.blogger.design.patterns.common.beans.Product;

/**
 * Created by sunyinjie on 2017/10/17.
 */
public class ConcreateProduct extends Product {
    @Override
    public void diff() {
        System.out.println("我的不同方法");
    }
}
