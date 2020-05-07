package com.learn.java.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义SQL 字符类型注解
 *
 * @author lfq
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLString {
    /**
     * 字段值
     * @return
     */
    String name() default "";

    String value() default "";

    /**
     * 字符串长度
     * @return
     */
    int length() default 255;

    /**
     * 字段约束
     * @return
     */
    Constraint constraint() default @Constraint;

    /**
     * 注释
     * @return
     */
    String comment() default "";
}
