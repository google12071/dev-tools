package com.learn.javasrc.basic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Parent
 * @Description: 类初始化顺序演示，父类
 * @Author lfq
 * @Date 2020/2/6
 **/
@Slf4j
@Data
public class Parent {
    /**
     * final变量，加载是即完成赋值
     */
    private static final int value = 1;

    /**
     * 父类静态变量
     */
    private static long num = 2L;

    /**
     * 实例变量
     */
    private String msg = "hello";

    static {
        num++;
        log.info("初始化父类静态代码块");
    }

    /**
     * 父类无参构造器
     */
    public Parent() {
        log.info("初始化父类无参构造器");
    }

    public Parent(String msg) {
        log.info("初始化父类有参构造器，参数:{}", msg);
    }

    {
        msg+="world";
        log.info("初始化父类普通代码块");
    }
}
