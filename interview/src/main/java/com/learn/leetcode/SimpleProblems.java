package com.learn.leetcode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;

/**
 * @ClassName SimpleProblems
 * @Description:程序员面试金典问题，简单题
 * @Author lfq
 * @Date 2020/2/14
 **/
@Slf4j
public class SimpleProblems {
    /**
     * 实现一个算法，确定一个字符串 s 的所有字符是否全都不同。
     *
     * @param str
     * @return
     */

    public static boolean isUnique(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        char[] chars = str.toCharArray();
        log.info("chars:{}", chars);
        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[j] == chars[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 给定两个字符串 s1 和 s2，请编写一个程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串。
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean CheckPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        log.info("chars1:{},chars2:{}", chars1, chars2);
        for (int i = 0; i < chars1.length; i++) {
            if (chars1[i] != chars2[i]) {
                log.info("char1:{},char2:{}", chars1[i], chars2[i]);
                return false;
            }
        }
        return true;
    }

    /**
     * URL化。编写一种方法，将字符串中的空格全部替换为%20。假定该字符串尾部有足够的空间存放新增字符，并且知道字符串的“真实”长度。
     *
     * @param S
     * @param length
     * @return
     */
    public static String replaceSpaces(String S, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (S.charAt(i) == ' ') {
                sb.append("%20");
            } else {
                sb.append(S.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 是否是回文排列:[每个字符出现的次数为偶数, 或者有且只有一个字符出现的次数为奇数时, 是回文的排列; 否则不是]
     *
     * @param s
     * @return
     */
    public static boolean canPermutePalindrome(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (!set.add(s.charAt(i))) {
                set.remove(s.charAt(i));
            }
        }
        return set.size() <= 1;
    }

    /**
     * 字符串压缩。利用字符重复出现的次数，编写一种方法，实现基本的字符串压缩功能。
     * 比如，字符串aabcccccaaa会变为a2b1c5a3。若“压缩”后的字符串没有变短，则返回原先的字符串。
     * 你可以假设字符串中只包含大小写英文字母（a至z）。
     *
     * @param S
     * @return
     */
    public static String compressString(String S) {
        StringBuilder sb = new StringBuilder();
        char[] chars = S.toCharArray();
        Map<Character, Integer> countMap = new TreeMap<>();
        for (int i = 0; i < chars.length; i++) {
            if (!countMap.containsKey(chars[i])) {
                countMap.put(chars[i], 1);
            } else {
                int count = countMap.get(chars[i]);
                countMap.put(chars[i], ++count);
            }
        }
        if (!countMap.isEmpty()) {
            for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
                sb.append(entry.getKey()).append(entry.getValue());
            }
        }
        return sb.toString().length() < S.length() ? sb.toString() : S;
    }

    public static String compressString2(String S) {
        if (S.isEmpty()) {
            return S;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(S.charAt(0));
        int count = 1;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == sb.charAt(sb.length() - 1)) {
                ++count;
            } else {
                sb.append(count);
                count = 1;
                sb.append(S.charAt(i));
            }
        }
        sb.append(count);
        return sb.length() >= S.length() ? S : sb.toString();
    }

    /**
     * 字符串轮转。给定两个字符串s1和s2，请编写代码检查s2是否为s1旋转而成（比如，waterbottle是erbottlewat旋转后的字符串）。
     * 解决方案：
     *   若s2由s1旋转而成，则必然存在字符串X，Y满足
     *   s1=XY
     *   s2=YX
     *   s1s1=XYXY
     *   s2必然是s1s1的字串
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isFlipedString(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        if (s1.length() == 0) {
            return true;
        }
        String s1s1 = s1 + s1;
        return s1s1.contains(s2);
    }

    public static void main(String[] args) {
        String s1 = "abcde  ", s2 = "";
        System.out.println(s1.indexOf(s2));
        System.out.println(isFlipedString(s1, s2));
    }
}
