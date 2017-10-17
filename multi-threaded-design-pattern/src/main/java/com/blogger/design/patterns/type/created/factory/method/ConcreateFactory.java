package com.blogger.design.patterns.type.created.factory.method;

import com.blogger.design.patterns.common.beans.Product;

/**
 * Created by sunyinjie on 2017/10/17.
 */
public class ConcreateFactory extends AbstractFactory{
    @Override
    public Product factoryMethod() {
        System.out.println("我这个工厂方法来初始化一个产品，也有可能是我的同事，其他的工厂实现");
        return new ConcreateProduct();
    }
}
