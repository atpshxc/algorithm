package com.algo.sort;

public class BinaryHeap {
    private int[] arr = {3, 2, 0, 4, 5, 4, 1};
//    private int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,0};

    public String toString() {
        int level = getTreeDepth();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= level; i++) {
            int index = (int) (Math.pow(2, i) - 1);
            if (index > arr.length) {
                break;
            }
//            int count = 2 * (level - i) - 1;
            int count = (int) Math.pow(level - i - 1, 2);
            sb.append(getAppendChar(count));
            int k = index;
            while (k - index < Math.pow(2, i) && k < arr.length) {
                sb.append(arr[k]).append(getAppendChar(2 * (level - i) - 1));
                k++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getAppendChar(int len) {
        if (len < 0) {
            return " ";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public int getTreeDepth() {
        int len = arr.length;
        return (int) (Math.log(len) / Math.log(2)) + 1;
    }

    public void buildMinHeap() {
        for (int i = (arr.length - 2) / 2; i >= 0; i--) {
            adjustDown(i, arr.length);
        }
    }

    public void buildMaxHeap() {
        for (int i = (arr.length - 2) / 2; i >= 0; i--) {
            adjustUp(i, arr.length);
        }
    }

    private void adjustDown(int parent, int len) {
        int temp = arr[parent];
        int leftChild = 2 * parent + 1;
        int child = leftChild;
        while (child < len) {
            int rightChild = child + 1;
            if (rightChild < len && arr[rightChild] < arr[child]) {
                child = rightChild;
            }
            if (temp < arr[child]) {
                break;
            }
            arr[parent] = arr[child];
            parent = child;
            child = 2 * parent + 1;
        }
        arr[parent] = temp;
    }

    private void adjustUp(int parent, int len) {
        int temp = arr[parent];
        int leftChild = 2 * parent + 1;
        int child = leftChild;
        while (child < len) {
            int rightChild = child + 1;
            if (rightChild < len && arr[rightChild] > arr[child]) {
                child = rightChild;
            }
            if (temp > arr[child]) {
                break;
            }
            arr[parent] = arr[child];
            parent = child;
            child = 2 * parent + 1;
        }
        arr[parent] = temp;
    }

    public static void main(String[] args) {
        BinaryHeap heap = new BinaryHeap();
        System.out.println(heap);
//        heap.buildMaxHeap();
        heap.buildMinHeap();
//        heap.sortAsc();
//        heap.sortDesc();
        System.out.println(heap);
    }

    public void sortDesc() {
        buildMinHeap();
        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            adjustDown(0, i);
        }
    }
    public void sortAsc() {
        buildMaxHeap();
        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            adjustUp(0, i);
        }
    }
}
