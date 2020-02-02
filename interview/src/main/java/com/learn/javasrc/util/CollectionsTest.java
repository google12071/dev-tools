package com.learn.javasrc.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lfq
 */
public class CollectionsTest {
    private static final LinkedList<Integer> numberList = new LinkedList<>();

    /**
     * 向头部插入元素
     *
     * @param args
     */
    public static void addFirst(Integer... args) {
        for (Integer num : args) {
            numberList.addFirst(num);
        }
    }

    /**
     * 向尾部插入元素
     *
     * @param args
     */
    public static void addLast(Integer... args) {
        for (Integer num : args) {
            numberList.addLast(num);
        }
    }

    public static void display() {
        numberList.stream().forEach(System.out::println);
    }


    public static void main(String[] args) {
        List<String> list = new ArrayList<String>() {{
            System.out.println("初始化数据");
            add("2");
            add("3");
            add("3");
            add("3");
            add("4");
            add("4");
        }};

        list.removeIf(next -> "4".equals(next) || "3".equals(next));
        list.stream().forEach(System.out::println);
    }
}
