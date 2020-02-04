package com.learn.javasrc.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Guava工具类操作
 *
 * @author lfq
 */
@Slf4j
public class GuavaCollectionTest {
    // 演示反转排序
    public static void testReverse() {
        List<String> list = new ArrayList<String>() {{
            add("10");
            add("20");
            add("30");
            add("40");
        }};
        log.info("反转之前：" + JSON.toJSONString(list));
        list = Lists.reverse(list);
        log.info("反转之后：" + JSON.toJSONString(list));
    }

    // 分组
    public static void testPartition() {
        List<String> list = new ArrayList<String>() {{
            add("10");
            add("20");
            add("30");
            add("40");
        }};
        log.info("分组之前：" + JSON.toJSONString(list));
        List<List<String>> list2 = Lists.partition(list, 3);
        log.info("分组之后：" + JSON.toJSONString(list2));
    }

    public static void mapDifferent() {
        // ImmutableMap.of 也是 Guava 提供初始化 Map 的方法，入参格式为 k1,v1,k2,v2,k3,v3……
        Map<String, String> leftMap = ImmutableMap.of("1", "1", "2", "2", "3", "3");
        Map<String, String> rightMap = ImmutableMap.of("2", "2", "3", "30", "4", "4");
        MapDifference difference = Maps.difference(leftMap, rightMap);
        log.info("左边 map 独有 key：{}", difference.entriesOnlyOnLeft());
        log.info("右边 map 独有 key：{}", difference.entriesOnlyOnRight());
        log.info("左右边 map 都有 key，并且 value 相等：{}", difference.entriesInCommon());
        log.info("左右边 map 都有 key，但 value 不等：{}", difference.entriesDiffering());
    }

    public static void main(String[] args) {
        mapDifferent();
    }
}
