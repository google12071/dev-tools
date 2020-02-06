package com.learn.javasrc.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ClassLoaderDemo
 * @Description:JVM参数追踪类信息
 * @Author lfq
 * @Date 2020/2/6
 **/
@Slf4j
public class ClassLoaderDemo {
    public static void main(String[] args) {
        String name = "fq";
        System.out.println("hello,my name is:" + name);
    }
}
