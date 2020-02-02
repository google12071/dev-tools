package com.learn.javasrc.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Arrays工具类
 *
 * @author lfq
 */
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
