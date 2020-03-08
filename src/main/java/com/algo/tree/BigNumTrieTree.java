package com.algo.tree;

import java.util.Random;

public class BigNumTrieTree {
    private class Node {
        private byte count = 1;
        private byte endCount;
        private Node[] childs;
    }

    /**
     * 是否允许重复
     */
    private boolean allowDump;
    private Node root;
    private long size;

    public BigNumTrieTree() {
        root = new Node();
    }

    public BigNumTrieTree(boolean allowDump) {
        this();
        this.allowDump = allowDump;
    }

    /**
     * 暂不支持负数
     *
     * @param number
     */
    public void insert(long number) {
        if (number < 0) {
            return;
        }
        size++;
        Node node = root;
        Node preNode = null;
        int n = 0;
        long val = number;
//        if (val < 0) {
//            val = ~val + 1;
//        }
        if (val < 10) {
            insertNode(node, (int) val);
            node.childs[(int) val].endCount++;
            return;
        }
        while (val > 0) {
            n = (int) (val % 10);
            insertNode(node, n);
            val /= 10;
            preNode = node;
            node = node.childs[n];
        }
        preNode.childs[n].endCount++;
        if (allowDump) {
            preNode.childs[n].count++;
        }
        //TODO 负数处理

    }

    private void insertNode(Node node, int n) {
        if (node.childs == null) {
            node.childs = new Node[10];
        }
        if (node.childs[n] == null) {
            node.childs[n] = new Node();
        }
    }

//    public List<String> listSort() {
//        List<String> list = new ArrayList<>();
//        root.keySet().stream().sorted().forEach(n -> {
//            list.addAll(getStrings(String.valueOf(n.value), root.get(n)));
//        });
//        return list;
//    }

//    private List<String> getStrings(String prefix, Map<Node, Map> m) {
//        List<String> result = new ArrayList<>();
//        if (m == null || m.isEmpty()) {
//            result.add(prefix);
//            return result;
//        }
//        m.keySet().stream().sorted().forEach(node -> {
//            StringBuilder sb = new StringBuilder(prefix);
//            sb.append(node.value);
//            result.addAll(getStrings(sb.toString(), m.get(node)));
//        });
//        return result;
//    }

    public boolean find(long num) {
        long val = num;
        if (val < 10) {
            if (root.childs[(int) val] != null && root.childs[(int) val].endCount > 0) {
                return true;
            }
        }
        int n;
        Node node = root;
        while (val > 0) {
            if (node == null) {
                return false;
            }
            if (node.endCount > 0) {
                return true;
            }
            n = (int) (val % 10);
            if (node.childs[n] != null && node.childs[n].endCount > 0) {
                return true;
            }
            node = node.childs[n];
            val /= 10;
        }
        return false;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    //9223372036854775807
    public static void main(String[] args) {
        BigNumTrieTree trieTree = new BigNumTrieTree(true);
        long start = System.currentTimeMillis();
        Random random = new Random(47);
        for (long i = 0; i < 47758070; i++) {
            trieTree.insert(i);
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        trieTree.find(47758054);
        System.out.println(System.currentTimeMillis() - start);
//        System.out.println(trieTree.listByPrefix("abk"));
//        sensitiveWordProcess();
//        System.out.println(trieTree.listSort());
    }
}
