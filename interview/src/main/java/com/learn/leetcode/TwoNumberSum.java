package com.learn.leetcode;

/**
 * @ClassName TwoNumberSum
 * @Description:
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

 * @Author lfq
 * @Date 2020/2/9
 **/
public class TwoNumberSum {
    public static void displayNum(long num){
        while (num > 0) {
            long value = num % 10;
            num = num / 10;
            System.out.println(value);
        }
    }
    public static void main(String[]args){
        displayNum(77891678);
    }
}
