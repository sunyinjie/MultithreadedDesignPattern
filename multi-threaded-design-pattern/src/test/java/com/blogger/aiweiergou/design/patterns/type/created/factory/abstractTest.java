package com.blogger.aiweiergou.design.patterns.type.created.factory;

import com.blogger.design.patterns.common.beans.AbstractProductA;
import com.blogger.design.patterns.common.beans.AbstractProductB;
import com.blogger.design.patterns.type.created.factory.abs.AbstractFactory;
import com.blogger.design.patterns.type.created.factory.abs.ConcreateFactory1;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sunyinjie on 2017/10/17.
 */
public class abstractTest {
    @Test
    public void testMain() {
        AbstractFactory factory1 = new ConcreateFactory1();
        AbstractProductA productA = factory1.createProductA();
        AbstractProductB productB = factory1.createProductB();
        Assert.assertNotNull(productA);
        Assert.assertNotNull(productB);
        productA.diff();
        productB.diff();
    }
}
