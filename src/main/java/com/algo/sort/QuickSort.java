package com.algo.sort;

/**
 * 快速排序
 */
public class QuickSort {
    public void sort(int[] a) {
        if (a.length < 2) {
            return;
        }
        sort(a, 0, a.length - 1);
    }

    private void sort(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = a[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (a[j] < p) {
                int t = a[j];
                a[j] = a[i];
                a[i++] = t;
            }
        }
        a[right] = a[i];
        a[i] = p;
        sort(a, left, i - 1);
        sort(a, i + 1, right);
    }

    public static void main(String[] args) {
        int[] a = {1, 4, 2, 6, 3, 68, 2, 5};
        QuickSort mergeSort = new QuickSort();
        mergeSort.sort(a);
        for (int v : a) {
            System.out.print(v + ",");
        }
    }
}
