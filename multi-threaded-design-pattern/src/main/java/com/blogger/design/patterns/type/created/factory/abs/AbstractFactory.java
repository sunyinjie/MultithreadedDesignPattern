package com.blogger.design.patterns.type.created.factory.abs;

import com.blogger.design.patterns.common.beans.AbstractProductA;
import com.blogger.design.patterns.common.beans.AbstractProductB;
import com.blogger.design.patterns.common.beans.Product;

/**
 * Created by sunyinjie on 2017/10/17.
 */
public abstract class AbstractFactory {
    public abstract AbstractProductA createProductA();
    public abstract AbstractProductB createProductB();
}
