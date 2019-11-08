package com.learn.java.common.javabase.concurrent.util.basic;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 请求Id生成器(单例模式)
 */
public class RequestIdGenerator implements CircularSeqGenerator {
    private final static RequestIdGenerator GENERATOR = new RequestIdGenerator();
    private final static int SEQUENCE_LIMIT = 999;
    private int sequence = -1;

    /**
     * 私有化
     */
    private RequestIdGenerator() {
    }

    /**
     * 生成循环递增的序列号
     *
     * @return
     */
    @Override
    public synchronized int nextSequence() {
        if (sequence >= SEQUENCE_LIMIT) {
            sequence = 0;
        } else {
            sequence++;
        }
        return sequence;
    }

    /**
     * 返回类的唯一实例
     *
     * @return
     */
    public static RequestIdGenerator getGenerator() {
        return GENERATOR;
    }

    /**
     * 生成一个新的Request ID
     *
     * @return
     */
    public String nextRequestId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("000");

        // 生成请求序列号
        int sequenceNo = nextSequence();

        return timestamp + df.format(sequenceNo);
    }
}
