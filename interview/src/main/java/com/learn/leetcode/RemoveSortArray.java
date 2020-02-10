package com.learn.leetcode;

/**
 * @ClassName RemoveSortArray
 * @Description:
 * @Author lfq
 * @Date 2020/2/9
 **/
public class RemoveSortArray {
    public static int removeDuplicatedNum(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }

    public static void main(String[] args) {
        int[] nums = {0, 0, 0, 1, 2, 2, 3, 3, 4, 5, 5};
        int num = removeDuplicatedNum(nums);
        System.out.println(num);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
        }
    }
}
