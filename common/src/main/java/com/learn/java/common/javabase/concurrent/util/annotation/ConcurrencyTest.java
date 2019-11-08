package com.learn.java.common.javabase.concurrent.util.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ConcurrencyTest {
    int iterations() default 2000;

    int thinkTime() default 0;
}
