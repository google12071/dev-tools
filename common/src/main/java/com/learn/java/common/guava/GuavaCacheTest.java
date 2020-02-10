package com.learn.java.common.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.*;
import com.google.common.collect.Lists;
import com.learn.java.common.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName GuavaCacheTest
 * @Description:GuavaCache缓存工具
 * @Author lfq
 * @Date 2020/2/10
 **/
@Slf4j
public class GuavaCacheTest {
    /**
     * 使用builder模式创建Cache
     */
    @Test
    public void buildCache() {
        Cache<String, User> cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
        cache.put("user1", new User("fq", 25));

        int count = 0;
        while (count++ < 10) {
            try {
                User user = cache.getIfPresent("user1");
                log.info("user:{}", JSON.toJSONString(user));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        LoadingCache<String, User> loadingCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS)
                .build(new CacheLoader<String, User>() {
                    @Override
                    public User load(String key) throws Exception {
                        return new User("base", 10);
                    }
                });
        try {
            User user2 = loadingCache.get("user2");
            log.info("user2:{}", JSON.toJSONString(user2));

            loadingCache.put("user3", new User("user3", 20));
            User user3 = loadingCache.get("user3");
            log.info("user3:{}", JSON.toJSONString(user3));

            //执行刷新操作
            loadingCache.refresh("user3");
            user3 = loadingCache.get("user3");
            log.info("user3:{}", JSON.toJSONString(user3));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 弱引用
     */
    @Test
    public void weakReference() {
        Cache<String, Object> cache = CacheBuilder.newBuilder().
                expireAfterWrite(10, TimeUnit.SECONDS).maximumSize(10).
                weakKeys().weakValues().build();
        Object value = new Object();
        cache.put("key1", value);
        System.out.println(cache.getIfPresent("key1"));
        //原对象不再有强引用
        value = new Object();
        System.gc();
        System.out.println(cache.getIfPresent("key1"));
    }

    /**
     * 显示清除key
     */
    @Test
    public void invalidKey() {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
        cache.put(1, "value1");
        cache.put(2, "value2");
        cache.put(3, "value3");
        cache.put(4, "value4");
        cache.put(5, "value5");
        cache.put(6, "value6");
        cache.put(7, "value7");

        String value1 = cache.getIfPresent(1);
        String value2 = cache.getIfPresent(2);
        String value3 = cache.getIfPresent(3);
        log.info("value1:{},value2:{},value3:{}", value1, value2, value3);

        List<Integer> keysList = Lists.newArrayList(1, 2, 4);
        cache.invalidateAll(keysList);
        value1 = cache.getIfPresent(1);
        value2 = cache.getIfPresent(2);
        value3 = cache.getIfPresent(3);
        log.info("value1:{},value2:{},value3:{}", value1, value2, value3);

        try {
            String value = cache.get(10, () -> "value10");
            log.info("value10:{}", value);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    /**
     * 移除监听器
     */
    @Test
    public void cacheListener() {
        RemovalListener<Integer, User> listener = notification -> log.info("cache remove,key:{},value:{}", notification.getKey(), notification.getValue());
        Cache<Integer, User> cache = CacheBuilder.newBuilder().maximumSize(3).removalListener(listener)
                .expireAfterWrite(10, TimeUnit.SECONDS).build();
        for (int i = 0; i < 10; i++) {
            cache.put(i, new User("test" + i, i + 1));
        }
        cache.invalidate(1);
    }

    /**
     * 加载数据
     */
    @Test
    public void loadData() {
        LoadingCache<String, Integer> loadingCache = CacheBuilder.newBuilder().
                expireAfterWrite(5, TimeUnit.SECONDS).maximumSize(5).
                build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return null;
                    }
                });
        for (int i = 0; i < 5; i++) {
            loadingCache.put("test" + i, i);
        }

        try {
            Integer value = loadingCache.get("test1", () -> 100);
            log.info("value:{}", value);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
