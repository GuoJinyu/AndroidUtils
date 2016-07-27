package com.acker.android;

import java.util.Arrays;

/*各种排序算法，默认从小到大排序*/
public class Sort {
    public static void main(String[] args) {
        int[] test = {12, 3, 6, 19, 11, 17, 23, 16, 25, 2, 0, 1, 45, 33, 7, 10};
        System.out.println("原始：" + Arrays.toString(test));
        bubbleSort(test);
        System.out.println("冒泡：" + Arrays.toString(test));
        selectSort(test);
        System.out.println("选择：" + Arrays.toString(test));
        insertSort(test);
        System.out.println("插入：" + Arrays.toString(test));
        shellSort(test);
        System.out.println("希尔：" + Arrays.toString(test));
        quickSort(test, 0, test.length - 1);
        System.out.println("快速：" + Arrays.toString(test));
        mergeSort(test, 0, test.length - 1);
        System.out.println("归并：" + Arrays.toString(test));
    }

    private static void swap(int[] source, int i, int j) {
        int temp = source[i];
        source[i] = source[j];
        source[j] = temp;
    }

    // 冒泡排序：让更大的数一直往上漂，漂到数组尾部，成为已排序部分
    // 稳定，假如一次扫描过程中没有交换就认为排序完成，则最好时间复杂度O(n)，否则最好时间复杂度O(n^2)，
    // 最坏时间复杂度O(n^2)，平均时间复杂度O(n^2)，空间复杂度O(1)
    public static void bubbleSort(int[] source) {
        int i, j;
        int n = source.length;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n - i - 1; j++) {
                if (source[j] > source[j + 1]) {
                    swap(source, j, j + 1);
                }
            }
        }
    }

    // 选择排序：每次在未排序的部分中选择一个最小的，放到已排序部分的末尾
    // 不稳定（选择完成，交换时可能造成顺序改变），时间复杂度都是O(n^2)，空间复杂度O(1)
    public static void selectSort(int[] source) {
        int i, j, min;
        int n = source.length;
        for (i = 0; i < n - 1; i++) {
            min = i;
            for (j = i + 1; j < n; j++) {
                if (source[min] > source[j]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(source, i, min);
            }
        }
    }

    // 插入排序：每次从未排序区取第一个数据，然后往前插入到排序区的合适位置
    // 稳定，最好时间复杂度O(n)，最坏时间复杂度O(n^2)，平均时间复杂度O(n^2)，空间复杂度O(1)
    public static void insertSort(int[] source) {
        int i, j, temp;
        int n = source.length;
        for (i = 1; i < n; i++) {
            temp = source[i];
            j = i - 1;
            while (j >= 0 && temp < source[j]) {
                source[j + 1] = source[j];
                j--;
            }
            source[j + 1] = temp;
        }
    }

    // Shell排序：改进的插入排序方法，对插入排序再加一个步长的循环就是希尔排序,适用于n较大时
    // 步长序列应该1.保证最终步长为1。2.尽量避免序列中的值互为倍数（尤其是相邻的值）
    // 利用了：1.n值较小时，直接插入排序的最好最坏时间复杂度相差不大。2.当数据基本有序时，直接插入排序的时间复杂度较小
    // 不稳定,时间复杂度取决于步长序列，空间复杂度O(1)
    public static void shellSort(int[] source) {
        int[] stepSeq = {5, 3, 2, 1};
        int i, j, k, temp, step;
        int n = source.length;
        for (k = 0; k < stepSeq.length; k++) {
            step = stepSeq[k];
            for (i = step; i < n; i++) {
                temp = source[i];
                j = i - step;
                while (j >= 0 && temp < source[j]) {
                    source[j + step] = source[j];
                    j = j - step;
                }
                source[j + step] = temp;
            }
        }
    }

    // 快速排序：分治法递归求解
    // 每次要找到一个中间位置，使得该点左边所有数值均比其小，右边均比其大，然后左右区间重复上述过程，直到所有子集只剩下一个元素为止
    // 不稳定，最坏时间复杂度O(n^2)(发生在每次划分过程产生的两个区间分别包含n-1个元素和1个元素的时候)
    // 最好时间复杂度O(nlogn)(每次区间都是等分)，平均时间复杂度O(nlogn)
    // 空间复杂度，主要是由于递归造成了栈空间的使用，一般而言，最好和平均为O(logn)，最差为O(n)
    public static void quickSort(int[] source, int low, int high) {
        int middle;
        if (low < high) {
            middle = partition(source, low, high);
            quickSort(source, low, middle - 1);
            quickSort(source, middle + 1, high);
        }
    }

    // 快速排序划分算法：区间第一个记录作为基准，从区间两端交替向中间扫描，直至i=j为止
    public static int partition(int[] source, int i, int j) {
        int point = source[i];
        while (i < j) {
            while (i < j && source[j] >= point) {
                j--;
            }
            if (i < j) {
                swap(source, i++, j);
            }
            while (i < j && source[i] <= point) {
                i++;
            }
            if (i < j) {
                swap(source, i, j--);
            }
        }
        return i;
    }

    // 归并排序：分治法递归求解，分为分割和合并两个部分
    // 将序列递归分割（二分或四分或。。）直至每个子序列只有一个值，然后相邻的子序列再递归合并，
    // 在合并的过程中完成对每个子序列的排序，即排好序的相邻子序列再次进行合并排序
    // 稳定，最好，最坏，平均时间复杂度都是O(nlogn)
    // 空间复杂度：使用了额外的空间，O(n)
    public static void mergeSort(int[] source, int low, int high) {
        if (low < high) {
            int mid = (high + low) / 2;
            // 两路归并，合并一次；如果是多路归并，同样是按照分割为几组，便合并几减一次。
            mergeSort(source, low, mid);
            mergeSort(source, mid + 1, high);
            merge(source, low, mid, mid + 1, high);
        }
    }

    public static void merge(int[] source, int lowA, int highA, int lowB,
                             int highB) {
        int i = lowA;
        int j = lowB;
        // 需要一个数组来存放merge结果
        int k = 0;
        int[] temp = new int[highB - lowA + 1];
        for (; i <= highA; i++) {
            for (; j <= highB; j++) {
                if (source[j] < source[i]) {
                    temp[k++] = source[j];
                } else {
                    break;
                }
            }
            temp[k++] = source[i];
        }
        // 只复制k个值，若第二段存在比第一段所有数都大的数，要将其及其后面部分在source中位置不变
        System.arraycopy(temp, 0, source, lowA, k);
    }
}
