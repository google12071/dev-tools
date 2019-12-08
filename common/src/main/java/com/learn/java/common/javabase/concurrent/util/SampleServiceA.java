package com.learn.java.common.javabase.concurrent.util;

import java.util.concurrent.CountDownLatch;

public class SampleServiceA extends AbstractService {

    public SampleServiceA(CountDownLatch latch) {
        super(latch);
    }

    @Override
    protected void doStart() throws Exception {
        // 模拟实际操作耗时
        Tools.randomPause(5000);

    }
}
