package com.blogger.design.patterns.type.created.factory.simple;

import com.blogger.design.patterns.common.beans.Product;

/**
 * Created by sunyinjie on 2017/10/17.
 */
public class ConcreateProductB extends Product {

    @Override
    public void diff() {
        System.out.println("b的定制化逻辑");
    }
}
