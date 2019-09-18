package com.learn.java.cache.caffeine;

public class DataObject {
    private String data;
    private static final int objectCounter = 0;

    public DataObject(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static int getObjectCounter() {
        return objectCounter;
    }

    public static DataObject get(String data) {
        return new DataObject(data);
    }
}

