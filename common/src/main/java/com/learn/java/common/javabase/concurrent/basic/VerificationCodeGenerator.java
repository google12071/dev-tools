package com.learn.java.common.javabase.concurrent.basic;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;

/**
 * 验证码生成器
 */

@Slf4j
public class VerificationCodeGenerator {

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
            log.info(Thread.currentThread().getName() + ",sendCode:" + code);
        }
    }

    public static void main(String[] args) {
        int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        log.info("numberOfProcessors:" + numberOfProcessors);
        for (int i = 0; i < numberOfProcessors; i++) {
            Thread t = new Thread(new GenerateThread());
            t.start();
        }
    }
}
