package com.learn.java.cache.guava;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.hash.BloomFilter;
import org.apache.curator.shaded.com.google.common.hash.Funnels;
import org.junit.Test;

/**
 * @ClassName BloomTest
 * @Description:布隆过滤器：
 *   数据结构：二进制位bit数组
 *   经过一些hash函数，put时将数组位置设置位1，布隆过滤器常用来判断，集合中是否存在某元素，用于去重复（比如新闻推荐已读内容去重复）
 *   若布隆过滤器判断元素不存在，则一定不存在；若判断元素存在，则可能不存在，因此判断存在性的时候，具有一定的误判率
 *
 * @Author lfq
 * @Date 2020/2/19
 **/
@Slf4j
public class BloomFilterTest {

    @Test
    public void basic(){
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 1500, 0.01);
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(1));
        filter.put(1);
        filter.put(2);
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(1));
        filter.put(1);
        filter.put(2);
    }
}
