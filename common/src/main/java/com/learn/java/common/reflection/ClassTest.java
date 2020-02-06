package com.learn.java.common.reflection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 反射机制测试类
 *
 * @author lfq
 */
@Slf4j
public class ClassTest {

    public static ClassTest newInstance() {
        return new ClassTest();
    }

    public static void createClassInstance1() {
        //对象.getClass() 方式获取Class对象
        Class<?> mClass = ClassTest.newInstance().getClass();
        //输出类所在的包路径
        System.out.println(" 通过对象.getClass（）方式，反射出类所在的包路径： " + mClass.getName());
    }

    public static void createClassInstance2() {
        //类名.class 方式获取Class对象
        Class<?> mClass = ClassTest.class;
        //输出类所在的包路径
        System.out.println(" 通过类名.Class 方式，反射出类所在的包路径： " + mClass.getName());
    }

    public static void createClassInstance3() {
        //Class.forName 方式获取Class对象
        Class<?> mClass = null;
        try {
            mClass = Class.forName("com.learn.java.common.reflection.ClassTest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //输出类所在的包路径
        System.out.println(" 通过Class.forName方式，反射出类所在的包路径： " + mClass.getName());
    }

    @Test
    public void isArray(){
        int[] arr = new int[10];
        boolean isArray = arr.getClass().isArray();
        log.info(String.valueOf(isArray));
    }



    public static void main(String[] args) {
        createClassInstance1();
        createClassInstance2();
        createClassInstance3();
    }
}
