package com.learn.java.common.javabase.concurrent.util;

import java.util.concurrent.CountDownLatch;

public class SampleServiceB extends AbstractService {
    public SampleServiceB(CountDownLatch latch) {
        super(latch);
    }

    @Override
    protected void doStart() throws Exception {
        Tools.randomPause(3000);
    }
}
