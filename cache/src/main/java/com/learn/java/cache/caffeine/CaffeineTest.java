package com.learn.java.cache.caffeine;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.*;
import com.learn.java.common.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName CaffineTest
 * @Description:Caffeine缓存(API和Guava基本保持一直)
 * @Author lfq
 * @Date 2020/2/11
 **/
@Slf4j
public class CaffeineTest {

    /**
     * Caffeine缓存填充具备3种策略，分别是手动、同步记载、异步加载
     */
    @Test
    public void build() {
        String key = "1";
        Cache<String, User> cache = Caffeine.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.SECONDS).build();
        //调用put方法手动填充
        cache.put(key, new User("fq", 26));
        User user = cache.getIfPresent(key);
        log.info("user={}", user);

        //key不存在则填充，推荐使用
        String key3 = "3";
        User user3 = cache.get(key3, s -> new User(s, 23));
        log.info("user3={}", user3);
    }

    /**
     * 同步加载
     */
    @Test
    public void synLoading() {
        LoadingCache<Integer, User> loadingCache = Caffeine.newBuilder().maximumSize(100).expireAfterWrite(10, TimeUnit.SECONDS).build(s -> new User("test", s));
        User user = loadingCache.get(1);
        log.info("user={}", user);
    }

    /**
     * 异步加载
     */
    @Test
    public void asnLoading(){
        AsyncLoadingCache<String, User> asyncLoadingCache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .buildAsync(this::getUser);

        String key = "A";
        asyncLoadingCache.get(key).thenAccept(u -> {
            log.info("key={},user={}", key, JSON.toJSONString(u));
        });
    }

    /**
     * 基于权重
     */
    @Test
    public void weight(){
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .maximumWeight(10)
                .weigher((k, v) -> 5)
                .build(k -> DataObject.get("Data for " + k));
        DataObject dataObject = cache.get("5");
        log.info("data:{}", dataObject);
    }

    @Test
    public void expireAfter(){
        Caffeine<String, User> userLoadingCache = Caffeine.newBuilder().expireAfter(new Expiry<String, User>() {
            @Override
            public long expireAfterCreate(@NonNull String key, @NonNull User value, long currentTime) {
                return 0;
            }

            @Override
            public long expireAfterUpdate(@NonNull String key, @NonNull User value, long currentTime, @NonNegative long currentDuration) {
                return 0;
            }

            @Override
            public long expireAfterRead(@NonNull String key, @NonNull User value, long currentTime, @NonNegative long currentDuration) {
                return 0;
            }
        });
    }

    private User getUser(String key){
        return new User(key,20);
    }
}
