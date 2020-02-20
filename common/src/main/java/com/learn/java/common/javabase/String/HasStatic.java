package com.learn.java.common.javabase.String;

/**
 * @ClassName BasicTest
 * @Description:
 * @Author lfq
 * @Date 2020/2/20
 **/
public class HasStatic {
    private static int x = 100;

    public static void main(String args[]) {
        HasStatic hs1 = new HasStatic();
        hs1.x++;
        HasStatic hs2 = new HasStatic();
        hs2.x++;
        hs1 = new HasStatic();
        hs1.x++;
        HasStatic.x--;
        System.out.println("x=" + x);
    }
}