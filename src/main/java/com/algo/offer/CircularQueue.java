package com.algo.offer;

public class CircularQueue {
    private String[] items;
    int head, tail;

    public CircularQueue(int size) {
        items = new String[size];
    }

    public boolean enqueue(String s) {
        if ((tail + 1) % items.length == head) {
            return false;
        }
        items[tail] = s;
        tail = (tail + 1) % items.length;
        return true;
    }

    public String dequeue() {
        if (tail == head) {
            return null;
        }
        String ret = items[head];
        head = (head + 1) % items.length;
        return ret;
    }
}
