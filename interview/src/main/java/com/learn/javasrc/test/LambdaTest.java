package com.learn.javasrc.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * lambda表达式测试
 *
 * @author lfq
 */
@Slf4j
public class LambdaTest {

    @Test
    public void simple() {
        Runnable r = () -> log.info(Thread.currentThread().getName() + ",say Hello");
        r.run();
    }
}
