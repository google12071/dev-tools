package com.learn.java.common.reflection;

import com.learn.java.common.pojo.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 反射机制测试机制
 *
 * @author lfq
 */
public class ReflectionDemo {
    public static void main(String[] args) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.learn.java.common.pojo.User");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //第一种方法，实例化默认构造方法，User必须提供无参构造函数,否则将抛异常
        if (clazz == null) {
            throw new RuntimeException("classNotFound");
        }
        try {
            User user = (User) clazz.newInstance();
            user.setAge(20);
            user.setName("Rollen");
            System.out.println(user);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            //获取带指定参数带Constructor
            Constructor cs1 = clazz.getConstructor(String.class, Integer.class);
            User user1 = (User) cs1.newInstance("fq", 25);
            user1.setAddress("浙江省温州市");
            user1.setId(1L);
            System.out.println(user1);

            //获取私有构造器
            Constructor cs2 = clazz.getDeclaredConstructor(Long.class, String.class);
            cs2.setAccessible(true);
            User user2 = (User) cs2.newInstance(2L, "zhangsan");
            user2.setMember(true);
            user2.setAddress("安徽合肥");
            System.out.println(user2);

            //获取所有构造包含private
            Constructor<?> cons[] = clazz.getDeclaredConstructors();
            // 查看每个构造方法需要的参数
            for (int i = 0; i < cons.length; i++) {
                //获取构造函数参数类型
                Class<?> clazzs[] = cons[i].getParameterTypes();
                System.out.println("构造函数[" + i + "]:" + cons[i].toString());
                System.out.print("参数类型[" + i + "]:(");
                for (int j = 0; j < clazzs.length; j++) {
                    if (j == clazzs.length - 1) {
                        System.out.print(clazzs[j].getName());
                    } else {
                        System.out.print(clazzs[j].getName() + ",");
                    }
                }
                System.out.println(")");
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
