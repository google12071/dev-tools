package com.learn.javasrc.generic;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName GenericFruit
 * @Description: 泛型水果测试类
 * @Author lfq
 * @Date 2020/2/5
 **/
@Slf4j
public class GenericFruit {

    class Fruit {
        @Override
        public String toString() {
            return "Fruit";
        }
    }

    class Apple extends Fruit {
        @Override
        public String toString() {
            return "Apple";
        }
    }

    class Person {
        @Override
        public String toString() {
            return "Person";
        }
    }

    public static class Generic<T> {
        public void showT1(T t) {
            log.info(t.toString());
        }

        public <T> void showT2(T t) {
            log.info(t.toString());
        }

        public <E> void showT3(E t) {
            log.info(t.toString());
        }
    }
}
