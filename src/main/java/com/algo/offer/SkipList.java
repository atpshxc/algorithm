package com.algo.offer;

import java.util.Random;

/**
 * 跳表的一种实现方法，用于练习。跳表中存储的是正整数，并且存储的是不重复的。
 */
public class SkipList {
    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        for (int i = 0; i < 100; i++) {
            skipList.insert(i);
        }
        skipList.find(39);
        skipList.delete(29);
        System.out.println(skipList);
    }

    private static final int MAX_LEVEL = 16;
    private int levelCount = 1;
    private Node head = new Node(MAX_LEVEL);
    private Random random = new Random();

    public void insert(int data) {
        int level = head.next[0] == null ? 1 : randomLevel();
        if (level > levelCount) {
            levelCount = level;
        }
        Node p = head;
        Node newNode = new Node(level);
        newNode.data = data;
        for (int i = level - 1; i >= 0; i--) {
            while (p.next[i] != null && p.next[i].data < data) {
                p = p.next[i];
            }
            newNode.next[i] = p.next[i];
            p.next[i] = newNode;
        }
    }

    public Node find(int data) {
        Node p = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.next[i] != null && p.next[i].data < data) {
                p = p.next[i];
            }
        }
        if (p.next[0] != null && p.next[0].data == data) {
            return p.next[0];
        } else {
            return null;
        }
    }

    public void delete(int data) {
        Node p = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.next[i] != null && p.next[i].data < data) {
                p = p.next[i];
            }
            if (p.next[i] != null && p.next[i].data == data) {
                p.next[i] = p.next[i].next[i];
            }
        }
    }

    public String toString() {
        StringBuilder[] sbs = new StringBuilder[levelCount];
        for (int i = 0; i < levelCount; i++) {
            sbs[i] = new StringBuilder();
        }
        for (int i = 0; i < levelCount; i++) {
            Node p = head;
            while (i < p.next.length && p.next[i] != null) {
                sbs[i].append(p.next[i].data).append(",");
                p = p.next[i];
            }
            sbs[i].append("\n");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = levelCount - 1; i >= 0; i--) {
            sb.append(sbs[i].toString());
        }
        return sb.toString();
    }

    private int randomLevel() {
        int level = 1;
        for (int i = 0; i < MAX_LEVEL; i++) {
            if (random.nextBoolean()) {
                level++;
                continue;
            }
            break;
        }
        return level;
    }
}

class Node {
    int data;
    Node[] next;

    Node(int level) {
        next = new Node[level];
    }
}
