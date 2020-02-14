package com.learn.javasrc.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

@Slf4j
public class ArraysTest {
    private static final List<SortDTO> dtoList = ImmutableList.of(
            new SortDTO("1"),
            new SortDTO("5"),
            new SortDTO("4"),
            new SortDTO("2"),
            new SortDTO("3"),
            new SortDTO("1")
    );

    /**
     * List转换为数组
     *
     * @param dtoList
     * @return
     */
    public static SortDTO[] toArray(List<SortDTO> dtoList) {
        return dtoList.toArray(new SortDTO[dtoList.size()]);
    }

    /**
     * 对象数组排序
     *
     * @param targetArr
     */
    public static void sort(SortDTO[] targetArr) {
        log.info("before sort:" + JSON.toJSONString(targetArr));
        Arrays.sort(targetArr, Comparator.comparing(SortDTO::getSortValue));
        log.info("after sort:" + JSON.toJSONString(targetArr));
    }

    /**
     * 二分查找元素[需指定排序规则]
     *
     * @param sortDTO   对象
     * @param targetArr 对象数组
     * @return
     */
    public static int search(SortDTO sortDTO, SortDTO[] targetArr) {
        return Arrays.binarySearch(targetArr, sortDTO, Comparator.comparing(SortDTO::getSortValue));
    }

    /**
     * 数组拷贝
     *
     * @param original
     * @param from
     * @param to
     * @return
     */
    public static SortDTO[] copy(SortDTO[] original, int from, int to) {
        return Arrays.copyOfRange(original, from, to);
    }

    /**
     * 遍历时删除异常
     */
    @Test
    public void removeWhenIterate(){
        List<String> list = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
            add("3");
            add("4");
        }};

        for (String s : list) {
            if ("3".equals(s)) {
                list.remove(s);
            }
        }
        log.info("after remove:{}", JSON.toJSONString(list));
    }

    public static void main(String[] args) {
        SortDTO[] targetArr = toArray(dtoList);
        sort(targetArr);
        int index = search(new SortDTO("2"), targetArr);
        System.out.println(index);

        SortDTO[] copyArr = copy(targetArr, 0, targetArr.length);
        System.out.println("copyArr:" + JSON.toJSONString(copyArr));

        SortDTO maxDto = Collections.max(dtoList, Comparator.comparing(SortDTO::getSortValue));
        System.out.println("max:" + JSON.toJSONString(maxDto));

        SortDTO minDto = Collections.min(dtoList, Comparator.comparing(SortDTO::getSortValue));
        System.out.println("min:" + JSON.toJSONString(minDto));
    }
}
