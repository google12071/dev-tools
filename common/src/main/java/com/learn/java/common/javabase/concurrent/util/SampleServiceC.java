package com.learn.java.common.javabase.concurrent.util;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class SampleServiceC extends AbstractService {
    public SampleServiceC(CountDownLatch latch) {
        super(latch);
    }

    @Override
    protected void doStart() {
        // 模拟实际操作耗时
        Tools.randomPause(6000);
        // 模拟服务器启动失败
        final Random random = new Random();
        int rand = random.nextInt(1000);
        if (rand <= 500) {
            throw new RuntimeException("服务器启动异常");
        }
    }
}
