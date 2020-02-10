package com.learn.java.common.javabase.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedAOP {
    boolean needAop() default false;

    int value() default 10;
}
