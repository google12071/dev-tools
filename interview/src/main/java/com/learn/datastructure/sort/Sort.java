package com.learn.datastructure.sort;

import java.util.Random;

/**
 * @author lfq
 */
public final class Sort {
    public static <T extends Comparable<? super T>> void mergeSort(T[] a) {
        T[] tmpArray = (T[]) new Comparable[a.length];
        mergeSort(a, tmpArray, 0, a.length - 1);
    }

    private static <T extends Comparable<? super T>> void mergeSort(T[] a, T[] tmpArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(a, tmpArray, left, center);
            mergeSort(a, tmpArray, center + 1, right);
            merge(a, tmpArray, left, center + 1, right);
        }
    }

    private static <T extends Comparable<? super T>> void merge(T[] a, T[] tmpArray, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;

        // Main loop
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (a[leftPos].compareTo(a[rightPos]) <= 0) {
                tmpArray[tmpPos++] = a[leftPos++];
            } else {
                tmpArray[tmpPos++] = a[rightPos++];
            }
        }

        // Copy rest of first half
        while (leftPos <= leftEnd) {
            tmpArray[tmpPos++] = a[leftPos++];
        }
        // Copy rest of right half
        while (rightPos <= rightEnd) {
            tmpArray[tmpPos++] = a[rightPos++];
        }

        // Copy tmpArray back
        for (int i = 0; i < numElements; i++, rightEnd--) {
            a[rightEnd] = tmpArray[rightEnd];
        }
    }

    public static <T extends Comparable<? super T>> void display(T[] arr) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>elements start>>>>>>>>>>>>>>>>>>>>>");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>elements end>>>>>>>>>>>>>>>>>>>>>");
    }


    public static void main(String[] args) {
        Integer[] randomArr = new Integer[10];
        Random r = new Random();
        for (int i = 0; i < randomArr.length; i++) {
            randomArr[i] = r.nextInt(100) + 1;
        }
        //排序前输出
        Sort.display(randomArr);
        System.out.println();
        //排序
        Sort.mergeSort(randomArr);
        //排序后输出
        Sort.display(randomArr);
    }
}
