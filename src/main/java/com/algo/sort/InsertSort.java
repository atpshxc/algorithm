package com.algo.sort;

public class InsertSort {
    public void sort(int[] a) {
        if (a.length < 2) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            int k = 0;
            while (a[i] > a[k]) {
                k++;
            }
            int v = a[i];
            move(a, i, k);
            a[k] = v;
        }
    }

    private void move(int[] a, int i, int k) {
        if (i == k) {
            return;
        }
        a[i] = a[i - 1];
        move(a, --i, k);
    }

    public static void main(String[] args) {
        int[] a = {3, 1, 4, 67, 1, 3, 7, 4};
        InsertSort selectSort = new InsertSort();
        selectSort.sort(a);
        for (int v : a) {
            System.out.print(v + ",");
        }
    }
}
