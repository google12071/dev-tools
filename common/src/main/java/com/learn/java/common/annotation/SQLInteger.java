package com.learn.java.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义SQL 整型注解
 *
 * @author lfq
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
    /**
     * 整型值
     *
     * @return
     */
    int value() default 0;

    /**
     * 主键是否自增
     *
     * @return
     */
    boolean idIncrement() default true;

    /**
     * 字段名
     * @return
     */
    String name() default "";

    /**
     * 字段约束
     *
     * @return
     */
    Constraint constraint() default @Constraint;

    /**
     * 字段注释
     *
     * @return
     */
    String comment() default "";
}
