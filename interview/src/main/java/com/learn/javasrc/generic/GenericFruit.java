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

    /**
     * Number并没有实现Comparable
     * 泛型方法实现取最小值
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<? super T>> T min(T[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        T min = values[0];
        for (int i = 1; i < values.length; i++) {
            if (min.compareTo(values[i]) > 0) {
                min = values[i];
            }
        }
        return min;
    }

    public static void main(String[] args) {
        Integer[] arr = {1, 3, 5, 7, 9};
        Integer min = GenericFruit.min(arr);
        System.out.println(min);
    }
}
