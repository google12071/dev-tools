package com.learn.java.common.javabase.concurrent.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

/**
 * 验证码生成器
 */

public class VerificationCodeGenerator {
    private final static Logger logger = LoggerFactory.getLogger(VerificationCodeGenerator.class);

    public static class GenerateThread implements Runnable {
        @Override
        public void run() {
            ThreadSpecificSecureRandom instance = ThreadSpecificSecureRandom.INSTANCE;
            instance.setSeed(1000);
            int nextInt = instance.nextInt(999999);
            DecimalFormat dft = new DecimalFormat("000000");
            String code = dft.format(nextInt);
            sendCode(code);
        }

        private void sendCode(String code) {
            logger.info(Thread.currentThread().getName() + ",sendCode:" + code);
        }
    }

    public static void main(String[] args) {
        int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        logger.info("numberOfProcessors:" + numberOfProcessors);
        for (int i = 0; i < numberOfProcessors; i++) {
            Thread t = new Thread(new GenerateThread());
            t.start();
        }
    }
}
