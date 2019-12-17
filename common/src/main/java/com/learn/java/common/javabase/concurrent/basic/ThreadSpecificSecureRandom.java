package com.learn.java.common.javabase.concurrent.basic;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public enum ThreadSpecificSecureRandom {
    INSTANCE;

    final static ThreadLocal<SecureRandom> randomThreadLocal = ThreadLocal.withInitial(() -> {
        SecureRandom srnd;
        try {
            srnd = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            srnd = new SecureRandom();
            new RuntimeException("No SHA1PRNG available,defaults to new SecureRandom()", e)
                    .printStackTrace();
        }
        // 通过以下调用来初始化种子
        srnd.nextBytes(new byte[20]);
        return srnd;
    });

    public int nextInt(int upperBound) {
        return randomThreadLocal.get().nextInt(upperBound);
    }

    public void setSeed(long seed) {
        randomThreadLocal.get().setSeed(seed);
    }
}
