package com.learn.javasrc.test;

import com.learn.javasrc.basic.Child;
import com.learn.javasrc.basic.Parent;

/**
 * @ClassName ClassInitTest
 * @Description:类初始化顺序(静态代码块，初始化仅会初始化1次)
 * @Author lfq
 * @Date 2020/2/6
 **/
public class ClassInitTest {
    public static void main(String[] args) {
        Parent p = new Child();
        p = new Child(3.3d, "china");
        Child c = new Child();
    }
}
