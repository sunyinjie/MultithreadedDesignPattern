package com.blogger.design.patterns.type.created.factory.abs;

import com.blogger.design.patterns.common.beans.*;

/**
 * Created by sunyinjie on 2017/10/17.
 */
public class ConcreateFactory1 extends AbstractFactory {

    private AbstractProductA productA;

    @Override
    public AbstractProductA createProductA() {
        productA = new ProductA1();
        return productA;
    }

    @Override
    public AbstractProductB createProductB() {
        return new ProductB1(productA);
    }
}
