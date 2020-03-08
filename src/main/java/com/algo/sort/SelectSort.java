package com.algo.sort;

public class SelectSort {
    public void sort(int[] a) {
        if (a.length < 2) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            int t = a[i];
            a[i] = a[min];
            a[min] = t;
        }
    }

    public static void main(String[] args) {
        int[] a = {3, 1, 4, 67, 1, 3, 7, 4};
        SelectSort selectSort = new SelectSort();
        selectSort.sort(a);
        for (int v : a) {
            System.out.print(v + ",");
        }
    }
}
