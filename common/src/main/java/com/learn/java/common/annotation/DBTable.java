package com.learn.java.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义表注解
 *
 * @author lfq
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    /**
     * 表名
     *
     * @return
     */
    String name() default "";

    /**
     * schema编码方式
     *
     * @return
     */
    String charSet() default "utf8mb4";

    /**
     * schema注释
     *
     * @return
     */
    String comment() default "";
}
