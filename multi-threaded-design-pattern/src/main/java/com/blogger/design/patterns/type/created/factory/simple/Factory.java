package com.blogger.design.patterns.type.created.factory.simple;

import com.blogger.design.patterns.common.beans.Product;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sunyinjie on 2017/10/17.
 */
public class Factory {
    private static final String TYPE_A = "A";
    private static final String TYPE_B = "B";

    public static Product createProduct(String type) {
        if (StringUtils.isBlank(type))
            throw new RuntimeException("类型错误");
        if (TYPE_A.equals(type)) {
            return new ConcreateProductA();
        }else if (TYPE_B.equals(type)) {
            return new ConcreateProductB();
        } else {
            throw new RuntimeException("不匹配的类型");
        }
    }
}
