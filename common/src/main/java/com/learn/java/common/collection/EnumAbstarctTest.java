package com.learn.java.common.collection;

/**
 * 含抽象方法枚举类测试
 */
public enum EnumAbstarctTest {
    FIRST {
        @Override
        public String getInfo() {
            return "first";
        }
    },
    SECOND {
        @Override
        public String getInfo() {
            return "second";
        }
    };

    public abstract String getInfo();

    public static void main(String[] args) {

        String first = EnumAbstarctTest.FIRST.getInfo();
        String second = EnumAbstarctTest.SECOND.getInfo();

        System.out.println("first:" + first + ",second:" + second);
    }
}
