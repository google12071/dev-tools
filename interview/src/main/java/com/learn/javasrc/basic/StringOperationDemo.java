package com.learn.javasrc.basic;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * String操作类源码分析
 *
 * @author lfq
 */
@Slf4j
public class StringOperationDemo {
    private static final String CHARSETNAME = "UTF-8";
    /**
     * 字符串转byte数组
     *
     * @param str
     * @param charsetName
     * @return
     */
    public static byte[] string2Bytes(String str, String charsetName) {
        if (str == null) {
            throw new RuntimeException("字符串格式错误");
        }
        try {
            return str.getBytes(charsetName == null ? CHARSETNAME : charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数组转成字符串
     *
     * @param bytes
     * @param charsetName
     * @return
     */
    public static String bytes2String(byte[] bytes, String charsetName) {
        try {
            return new String(bytes, charsetName == null ? CHARSETNAME : charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 首字母小写
     *
     * @param s
     * @return
     */
    public static String firstLowerCase(String s) {
        return s.substring(0).toLowerCase() + s.substring(1);
    }

    /**
     * 字符串替换
     *
     * @param str
     */
    public static void replace(String str) {
        log.info("替换之前 :{}", str);
        str = str.replace('l', 'd');
        log.info("替换所有字符 :{}", str);
        str = str.replaceAll("d", "l");
        log.info("替换全部 :{}", str);
        str = str.replaceFirst("l", "f");
        log.info("替换第一个 l :{}", str);
    }

    /**
     * 字符串拆分
     *
     * @param separator
     */
    public static List<String> split(String str, String separator) {
        return Splitter.on(separator).omitEmptyStrings().trimResults().splitToList(str);
    }

    /**
     * 字符串合并
     *
     * @param
     */
    public static String join(String separator, List<String> list) {
        return Joiner.on(separator).skipNulls().join(list);
    }

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList(new String[]{"hello", null, "world", "welcome", null, null, "come"});
        System.out.println(join(",", list));
    }
}
