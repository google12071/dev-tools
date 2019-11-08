package com.learn.java.common.javabase.concurrent.util.annotation;

import java.lang.annotation.*;


@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Observer {
	Expect[] value();
}
