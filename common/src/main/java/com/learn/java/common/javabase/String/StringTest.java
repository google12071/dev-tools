package com.learn.java.common.javabase.String;

/**
 * String类相关
 *
 * @author lfq
 */
public class StringTest {
    private static final String STR = "abcdefg";

    public static void immutable(String s) {
        System.out.println("before:" + s.hashCode());
        s = s + STR;
        System.out.println("after:" + s.hashCode());
    }

    /**
     * 不可变String
     *
     * @param str
     * @return
     */
    public static String appendStr(String str) {
        return str + STR;
    }

    /**
     * 可变的StringBuilder
     *
     * @param builder
     * @return
     */
    public static StringBuilder appendSb(StringBuilder builder) {
        return builder.append(STR);
    }

    public static void main(String[] args) {
        String s = "abc";
        immutable(s);
        String appendStr = appendStr(s);
        System.out.println("s:" + s + ",appendStr:" + appendStr);

        StringBuilder builder = new StringBuilder(s);
        StringBuilder newBuilder = appendSb(builder);
        System.out.println("builder:" + builder.toString()+",newBuilder:"+newBuilder);

        final char[] value = {'a', 'b', 'c'};
        System.out.println(value);
        value[0]='c';
        System.out.println(value);

        String s1 = "123";
        String s2 = new String("123");
        System.out.println(s1.equals(s2));
        System.out.println(s1 == s2);
        System.out.println(s1.intern().equals(s2.intern()));

        String str1 = "123";
        String str2 = "345";
        System.out.println("str1HashCode:" + str1.hashCode() + ",str2HashCode:" + str2.hashCode());
    }
}
