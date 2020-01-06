package com.learn.ms.zk;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.UnsupportedEncodingException;

/**
 * 自定义zookeeper序列化反序列化规则
 */
public class CustomSerializer implements ZkSerializer {
    @Override
    public byte[] serialize(Object obj) throws ZkMarshallingError {
        if (obj == null) {
            throw new RuntimeException("序列化对象为空");
        }

        String str = (String) obj;
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
