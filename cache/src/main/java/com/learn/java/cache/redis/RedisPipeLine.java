package com.learn.java.cache.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Redis流水线技术
 */
public class RedisPipeLine {

    private static final Logger logger = LoggerFactory.getLogger(RedisPipeLine.class);

    private static final int count = 100000;

    private static Jedis jedis = RedisUtil.getRedis();

    /**
     * pipeLine执行100000次读写
     *
     * @return
     */
    public static long pipeLineCost() {
        long start = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < count; i++) {
            pipeline.set(String.valueOf(i), String.valueOf(i));
        }
        pipeline.sync();
        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 正常情况下执行100000次读写
     * @return
     */
    public static long normalCost(){
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            jedis.set(String.valueOf(i), String.valueOf(i));
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 清理缓存
     */
    public static void invalidValue() {
        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < count; i++) {
            pipeline.del(String.valueOf(i));
        }
        pipeline.sync();
    }

    public static void main(String[] args) {
        long pipeLineCost = RedisPipeLine.pipeLineCost();
        long normalCost = RedisPipeLine.normalCost();
        logger.info("pipeLineCost:" + pipeLineCost + ",normalCost:" + normalCost);

        //删除测试数据
        RedisPipeLine.invalidValue();
    }
}
