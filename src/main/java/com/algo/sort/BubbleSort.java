package com.algo.sort;

public class BubbleSort {
    public void sort(int[] a) {
        if (a.length < 2) {
            return;
        }
        for (int i = a.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (a[j + 1] < a[j]) {
                    int t = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = t;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {3, 1, 4, 67, 1, 3, 7, 4};
        BubbleSort selectSort = new BubbleSort();
        selectSort.sort(a);
        for (int v : a) {
            System.out.print(v + ",");
        }
    }
}
