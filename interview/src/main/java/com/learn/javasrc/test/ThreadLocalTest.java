package com.learn.javasrc.test;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;

import java.util.Map;

/**
 * ThreadLocal测试
 *
 * @author lfq
 */
@Slf4j
public class ThreadLocalTest {
    private static final ThreadLocal<Map<String, String>> context = new ThreadLocal<>();

    @Test
    public void testThread() {
        // 从上下文中拿出 Map
        Map<String, String> contextMap = context.get();
        if (MapUtils.isEmpty(contextMap)) {
            contextMap = Maps.newHashMap();
        }

        contextMap.put("key1", "value1");
        context.set(contextMap);
        log.info("key1，value1被放到上下文中");
        // 从上下文中拿出刚才放进去的数据
        getFromContext();
    }

    private String getFromContext() {
        String value1 = context.get().get("key1");
        log.info("从 ThreadLocal 中取出上下文，key1 对应的值为：{}", value1);
        return value1;
    }
}
