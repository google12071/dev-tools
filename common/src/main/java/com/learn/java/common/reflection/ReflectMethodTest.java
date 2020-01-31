package com.learn.java.common.reflection;

import com.learn.java.common.pojo.Student;

import java.lang.reflect.Method;

/**
 * @author lfq
 */
public class ReflectMethodTest {
    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("com.learn.java.common.pojo.Student");

            Student student = (Student) clazz.newInstance();
            student.setName("fq");

            //根据参数获取public的Method,包含继承自父类的方法
            Method method = clazz.getMethod("introduce", float.class);

            System.out.println("method:" + method);

            //获取所有public的方法:
            Method[] methods = clazz.getMethods();
            for (Method m : methods) {
                System.out.println("m::" + m);
            }

            Method targetMethod1 = clazz.getMethod("introduce", float.class);
            targetMethod1.invoke(student, 181.5f);

            System.out.println("=========================================");
            //获取当前类的方法包含private,该方法无法获取继承自父类的method
            Method method1 = clazz.getDeclaredMethod("introduce");
            System.out.println("method1::" + method1);
            //获取当前类的所有方法包含private,该方法无法获取继承自父类的method
            Method[] methods1 = clazz.getDeclaredMethods();
            for (Method m : methods1) {
                System.out.println("m1::" + m);
            }
            Method targetMethod2 = clazz.getMethod("introduce");
            targetMethod2.invoke(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
