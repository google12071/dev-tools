package com.learn.java.cache.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

    private Properties properties;
    private String filePath;

    public PropertyUtils(String filePath) {
        try {
            filePath = filePath;
            properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = PropertyUtils.class.getClassLoader().getResourceAsStream(filePath);
            // 使用properties对象加载输入流
            properties.load(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return properties == null ? null : properties.getProperty(key);
    }

    public Integer getInt(String key) {
        return properties == null ? null : Integer.parseInt(properties.getProperty(key));
    }

    public Long getLong(String key) {
        return properties == null ? null : Long.parseLong(properties.getProperty(key));
    }

    public Boolean getBoolean(String key) {
        return properties == null ? null : Boolean.parseBoolean(properties.getProperty(key));
    }

    public static PropertyUtils getUtil(String path) {
        return new PropertyUtils(path);
    }
}

