package com.learn.javasrc.basic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Child
 * @Description:类初始化顺序演示，子类
 * @Author lfq
 * @Date 2020/2/6
 **/
@Data
@Slf4j
public class Child extends Parent {
    /**
     * 子类final变量
     */
    private static final float f = 1.3f;

    /**
     * 子类静态变量
     */
    private static boolean flag = true;

    /**
     * 子类普通实例变量
     */
    private double d = 3.7d;

    /**
     * 子类实例变量
     */
    private String msg = "hello";

    static {
        flag = false;
        log.info("初始化子类静态代码块");
    }

    public Child() {
        log.info("初始化子类无参构造器");
    }

    public Child(double d, String msg) {
        this.d = d;
        this.msg = msg;
        log.info("初始化子类有参构造器，参数d:{},参数msg:{}", d, msg);
    }

    {
        msg += "welcome";
        log.info("初始化子类普通代码块");
    }
}
