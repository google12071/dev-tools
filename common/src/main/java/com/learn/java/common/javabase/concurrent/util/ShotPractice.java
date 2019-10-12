package com.learn.java.common.javabase.concurrent.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 士兵射击训练模拟
 */
public class ShotPractice {

    private static final Logger logger = LoggerFactory.getLogger(ShotPractice.class);


    /**
     * 士兵
     */
    static class Soldier {
        private int seqNo;//序号

        public Soldier(int seqNo) {
            this.seqNo = seqNo;
        }

        public void fire(){
             logger.info(this+"start firing...");
            ThreadTools.randomPause(5);
        }

        @Override
        public String toString() {
            return "Soldier-" + seqNo;
        }
    }
}
