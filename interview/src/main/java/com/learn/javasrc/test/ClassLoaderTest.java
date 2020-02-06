package com.learn.javasrc.test;

import com.learn.javasrc.jvm.MyClassLoader;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @ClassName ClassLoaderTest
 * @Description:自定义类加载器加载测试
 * @Author lfq
 * @Date 2020/2/6
 **/
@Slf4j
@Data
public class ClassLoaderTest {
    private static final String classPath = "/Users/lfq/file/private/projects/dev-tools/dev-tools/interview/target/classes/com/learn/javasrc/jvm";

    public static void main(String[] args) {
        MyClassLoader classLoader = new MyClassLoader(classPath);
        try {
            Class clazz = classLoader.loadClass("A");
            Object object = clazz.newInstance();
            Method method = clazz.getMethod("sum", Integer.class, Integer.class);
            Integer sum = (Integer) method.invoke(object, 1, 3);
            log.info("sum is:{}", sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
