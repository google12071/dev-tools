package com.learn.javasrc.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author lfq
 */
@Slf4j
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

    public static void testArrayToList() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6};
        List<Integer> list = Arrays.asList(array);

        // 坑1：修改数组的值，会直接影响原 list
        log.info("数组被修改之前，集合第一个元素为：{}", list.get(0));
        array[0] = 10;
        log.info("数组被修改之前，集合第一个元素为：{}", list.get(0));

        // 坑2：使用 add、remove 等操作 list 的方法时，
        // 会报 UnsupportedOperationException 异常
        list.add(7);
    }

    public static void testMap() {
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

        Map<String, Integer> map = new HashMap<>();
        map.putIfAbsent("1", 2);
        map.putIfAbsent("1", 3);
        map.putIfAbsent("3", 4);
        map.putIfAbsent("3", 5);
        map.put("4", 123431);
        System.out.println(JSON.toJSONString(map));

        Integer value = map.getOrDefault("1", 5);
        System.out.println(value);

        map.entrySet().removeIf(entry -> entry.getValue() != null && entry.getValue() == 2);
        System.out.println(JSON.toJSONString(map));
    }

    public static void testListToArray() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
        }};

        // 下面这行被注释的代码这么写是无法转化成数组的，无参 toArray 返回的是 Object[],
        // 无法向下转化成 List<Integer>，编译都无法通过
        // List<Integer> list2 = list.toArray();

        // 演示有参 toArray 方法，数组大小不够时，得到数组为 null 情况
        Integer[] array0 = new Integer[2];
        list.toArray(array0);
        log.info("toArray 数组大小不够，array0 数组[0] 值是{},数组[1] 值是{},", array0[0], array0[1]);

        // 演示数组初始化大小正好，正好转化成数组
        Integer[] array1 = new Integer[list.size()];
        list.toArray(array1);
        log.info("toArray 数组大小正好，array1 数组[3] 值是{}", array1[3]);

        // 演示数组初始化大小大于实际所需大小，也可以转化成数组
        Integer[] array2 = new Integer[list.size() + 2];
        list.toArray(array2);
        log.info("toArray 数组大小多了，array2 数组[3] 值是{}，数组[4] 值是{}", array2[3], array2[4]);
    }

    public static void main(String[] args) {
        testListToArray();
    }
}
