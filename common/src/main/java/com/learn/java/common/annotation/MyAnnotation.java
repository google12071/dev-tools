package com.learn.java.common.annotation;

import java.lang.annotation.*;

/**
 * @ClassName MyAnnotation
 * @Description:自定义注解
 * @Author lfq
 * @Date 2020/3/10
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    int value() default 10;

    String name() default "zhangsan";
}
