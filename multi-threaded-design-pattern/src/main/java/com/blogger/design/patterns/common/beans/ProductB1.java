package com.blogger.design.patterns.common.beans;

/**
 * Created by sunyinjie on 2017/10/17.
 */
public class ProductB1 extends AbstractProductB {
    private AbstractProductA productA1;

    public ProductB1(AbstractProductA productA1) {
        this.productA1 = productA1;
    }

    @Override
    public void diff() {
        System.out.println("我是b1");
        System.out.println("我还依赖的productA");
        productA1.diff();
    }
}
