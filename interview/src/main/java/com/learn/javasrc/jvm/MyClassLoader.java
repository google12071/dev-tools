package com.learn.javasrc.jvm;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @ClassName MyClassLoader
 * @Description:自定义ClassLoder，模拟双亲委派模式
 * @Author lfq
 * @Date 2020/2/6
 **/
public class MyClassLoader extends ClassLoader {

    /**
     * 类路径,从文件中读取class二进制文件
     */
    private String classPath;

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    /**
     * 从文件流中读取class文件
     *
     * @param name
     * @return
     */
    private byte[] readClass(String name) {
        try {
            String inPath = classPath + "/" + name + ".class";
            FileInputStream fis = new FileInputStream(inPath);
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    /**
     * 重写findClass方法，让加载的时候调用findClass方法
     *
     * @param name 全限定类名
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) {
        //文件中读取class二进制流
        byte[] classBytes = readClass(name);
        //将字节码装入内存
        return defineClass(name, classBytes, 0, classBytes.length);
    }
}
