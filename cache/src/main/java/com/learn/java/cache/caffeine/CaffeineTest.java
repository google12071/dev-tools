package com.learn.java.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.learn.java.common.pojo.User;
import lombok.extern.slf4j.Slf4j;
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
    public void build(){
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



    public static void main(String[] args) {

    }
}
