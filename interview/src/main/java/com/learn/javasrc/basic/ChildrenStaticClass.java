package com.learn.javasrc.basic;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 静态子类
 *
 * @author lfq
 */
@Slf4j
public class ChildrenStaticClass extends ParentStaticClass {
    private static final List<String> childrenList = Lists.newArrayList("child");


    static {
        log.info("静态子类代码块初始化");
    }

    {
        log.info("非静态代码块初始化");
    }

    public ChildrenStaticClass() {
        super.setA(5);
        log.info("子类构造器初始化");
    }


    public static void staticMethod() {
        log.info("子类静态方法初始化");
    }

    @Override
    public int getA() {
        return super.getA();
    }

    public static void main(String[] args) {
        log.info("main方法初始化");
        ChildrenStaticClass child = new ChildrenStaticClass();
        ParentStaticClass parent = new ParentStaticClass();
        log.info("child:{},parent:{}", child.getA(),parent.getA());
    }
}
