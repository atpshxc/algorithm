package com.algo.sort;

/**
 * 希尔排序
 */
public class ShellSort {
    public void sort(int[] a) {
        if (a.length < 2) {
            return;
        }
        int gap = a.length >> 1;
        while (gap >= 1) {
            sort(a, gap);
            gap >>= 1;
        }
    }

    /**
     * 插入排序
     */
    private void sort(int[] a, int gap) {
        for (int i = 0; i < gap; i++) {
            for (int j = i + 1; j < a.length; j += gap) {
                int k = 0;
                while (a[j] > a[k]) {
                    k++;
                }
                int v = a[j];
                move(a, j, k);
                a[k] = v;
            }
        }
    }

    private void move(int[] a, int i, int k) {
        if (i == k) {
            return;
        }
        a[i] = a[i - 1];
        move(a, --i, k);
    }

    public static int[] shellSort(int nums[]) {
        int n = nums.length;
        for (int dk = n / 2; dk >= 1; dk = dk / 2) {
            for (int i = dk; i < n; ++i) {
                if (nums[i] < nums[i - dk]) {
                    int tmp = nums[i], j;
                    for (j = i - dk; j >= 0 && tmp < nums[j]; j -= dk) {
                        nums[j + dk] = nums[j];
                    }
                    nums[j + dk] = tmp;
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] a = {3, 1, 4, 67, 1, 3, 7, 4};
        shellSort(a);
        ShellSort selectSort = new ShellSort();
//        selectSort.sort(a);
        for (int v : a) {
            System.out.print(v + ",");
        }
    }
}
