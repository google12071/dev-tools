package com.learn.javasrc.basic;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态父类
 *
 * @author lfq
 */
@Slf4j
public class ParentStaticClass {
    private static final List<String> parentList = Lists.newArrayList("father");

    static {
        log.info("静态父类代码块初始化");
    }

    public ParentStaticClass() {
        log.info("父类构造器初始化");
    }

    public static void staticMethod() {
        log.info("父类静态方法初始化");
    }
}
