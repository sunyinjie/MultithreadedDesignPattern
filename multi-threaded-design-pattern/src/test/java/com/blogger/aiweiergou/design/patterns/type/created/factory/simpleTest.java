package com.blogger.aiweiergou.design.patterns.type.created.factory;

import com.blogger.design.patterns.common.beans.Product;
import com.blogger.design.patterns.type.created.factory.simple.Factory;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by sunyinjie on 2017/10/17.
 */
public class simpleTest {
    @Test
    public void testMain() {
        Product a = Factory.createProduct("A");
        Assert.assertNotNull(a);
        a.same();
        a.diff();

        try {
            Product c = Factory.createProduct("C");
            Assert.assertNotNull(c);
            c.same();
            c.diff();
        } catch (Exception e) {
            System.out.println("初始化C异常:" + e);
        }

        Product b = Factory.createProduct("B");
        Assert.assertNotNull(b);
        b.same();
        b.diff();

    }
    
}
