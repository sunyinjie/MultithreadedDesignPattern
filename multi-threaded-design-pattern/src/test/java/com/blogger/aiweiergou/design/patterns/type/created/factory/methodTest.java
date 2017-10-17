package com.blogger.aiweiergou.design.patterns.type.created.factory;

import com.blogger.design.patterns.common.beans.Product;
import com.blogger.design.patterns.type.created.factory.method.AbstractFactory;
import com.blogger.design.patterns.type.created.factory.method.ConcreateFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sunyinjie on 2017/10/17.
 */
public class methodTest {
    @Test
    public void testMain() {
        AbstractFactory factory = new ConcreateFactory();
        Product product = factory.factoryMethod();
        Assert.assertNotNull("初始化产品成功", product);
        product.same();
        product.diff();
    }
}
