package com.algo.sort;

/**
 * 归并排序
 */
public class MergeSort {
    public void sort(int[] a) {
        if (a.length < 2) {
            return;
        }
        sort(a, 0, a.length - 1);
    }

    private void sort(int[] a, int left, int right) {
        if (left < right) {
            int mid = (right + left) / 2;
            sort(a, left, mid);
            sort(a, mid + 1, right);
            merge(a, 0, mid, right);
        }
    }

    private void merge(int[] a, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (a[i] <= a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        while (j <= right) {
            temp[k++] = a[j++];
        }
        for (int x = 0; x < temp.length; x++) {
            a[left + x] = temp[x];
        }
    }

    public static void main(String[] args) {
        int[] a = {3, 1, 4, 67, 1, 3, 7, 4};
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(a);
        for (int v : a) {
            System.out.print(v + ",");
        }
    }
}
