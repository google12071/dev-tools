package com.learn.java.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义约束
 *
 * @author lfq
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {
    /**
     * 是否允许为空
     *
     * @return
     */
    boolean allowNull() default true;

    /**
     * 是否唯一
     *
     * @return
     */
    boolean unique() default false;

    /**
     * 是否是主键
     * @return
     */
    boolean primary() default false;
}
