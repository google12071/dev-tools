package com.learn.java.common.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName SimpleArgs
 * @Description: JVM参数配置
 * @Author lfq
 * @Date 2020/2/6
 **/
@Slf4j
public class SimpleArgs {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println("参数" + i + 1 + ":" + args[i]);
        }
        System.out.println("-Xmx" + Runtime.getRuntime().maxMemory() / 1000 / 1000 + "M");
    }
}
