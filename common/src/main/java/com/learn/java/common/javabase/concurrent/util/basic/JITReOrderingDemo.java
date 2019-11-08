package com.learn.java.common.javabase.concurrent.util.basic;


import com.learn.java.common.javabase.concurrent.util.TestRunner;
import com.learn.java.common.javabase.concurrent.util.annotation.Actor;
import com.learn.java.common.javabase.concurrent.util.annotation.ConcurrencyTest;
import com.learn.java.common.javabase.concurrent.util.annotation.Expect;
import com.learn.java.common.javabase.concurrent.util.annotation.Observer;

/**
 * JIT编译器重排序Demo
 */
@ConcurrencyTest(iterations = 200000)
public class JITReOrderingDemo {

    private int data = 1;

    private Helper helper;

    @Actor
    public void createHelper() {
        this.helper = new Helper(data);
    }


    @Observer({
            @Expect(desc = "Helper is null", expected = -1),
            @Expect(desc = "Helper is not null,but it is not initialized", expected = 0),
            @Expect(desc = "Only 1 field of Helper instance was initialized", expected = 1),
            @Expect(desc = "Only 2 fields of Helper instance were initialized", expected = 2),
            @Expect(desc = "Only 3 fields of Helper instance were initialized", expected = 3),
            @Expect(desc = "Helper instance was fully initialized", expected = 4)})

    public synchronized int consume() {
        int sum = 0;
        /*
         * 由于我们未对共享变量helper进行任何处理（比如采用volatile关键字修饰该变量），
         * 因此，这里可能存在可见性问题，即当前线程读取到的变量值可能为null。
         */

        final Helper observedHelper = helper;
        if (null == observedHelper) {
            sum = -1;
        } else {
            sum = observedHelper.loadA + observedHelper.loadB + observedHelper.loadC + observedHelper.loadD;
        }

        return sum;
    }

    static class Helper {
        int loadA;
        int loadB;
        int loadC;
        int loadD;

        public Helper(int data) {
            this.loadA = data;
            this.loadB = data;
            this.loadC = data;
            this.loadD = data;
        }

        @Override
        public String toString() {
            return "Helper[" +
                    "loadA=" + loadA +
                    ", loadB=" + loadB +
                    ", loadC=" + loadC +
                    ", loadD=" + loadD +
                    ']';
        }
    }

    public static void main(String[] args) {
        // 调用测试工具运行测试代码
        try {
            TestRunner.runTest(JITReOrderingDemo.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
