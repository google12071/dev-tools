package com.learn.datastructure;

import java.util.Stack;

/**
 * @ClassName DoubleStack2Queue
 * @Description:利用2个栈，设计一个队列
 * @Author lfq
 * @Date 2020/2/20
 **/
public class DoubleStack2Queue {
    private static Stack<Integer> stack1 = new Stack<>();
    private static Stack<Integer> stack2 = new Stack<>();

    /**
     * 入队
     *
     * @param value
     */
    public static void offer(Integer value) {
        stack1.push(value);
    }

    /**
     * 出队
     *
     * @return
     */
    public static Integer poll() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    public static Integer size() {
        return stack1.size() + stack2.size();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            DoubleStack2Queue.offer(i);
        }

        while (DoubleStack2Queue.size() != 0) {
            System.out.println("value:" + DoubleStack2Queue.poll());
        }

    }
}
