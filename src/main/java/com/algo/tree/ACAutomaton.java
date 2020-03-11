package com.algo.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * AC自动机
 */
public class ACAutomaton {
    private AcNode root;

    public ACAutomaton() {
        root = new AcNode('/');
        root.fail = null;
    }

    public static void main(String[] args) {
        ACAutomaton trieTree = new ACAutomaton();
        trieTree.insert("麻痹");
        trieTree.insert("妈的");
        trieTree.insert("日");
        trieTree.insert("草");
        trieTree.insert("操");
        String s = "抵扣可麻痹的，上课的妈的，累死了日，操五金草";
        trieTree.buildFailPoint();
        System.out.println(trieTree.sensitiveWordProcess(s, '*'));
    }

    public void insert(String s) {
        if (s == null || s.length() == 0) {
            return;
        }
        char[] chars = s.toCharArray();
        Map<Character, AcNode> child = root.child;
        AcNode node = null;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (child.containsKey(c)) {
                node = child.get(c);
            } else {
                node = new AcNode(c);
                child.put(c, node);
            }
            child = node.child;
        }
        node.isEndingChar = true;
        node.len = s.length();
    }

    /**
     * 判断一个字符串是否在字典树中
     *
     * @param s
     * @return
     */
    public boolean find(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        char[] chars = s.toCharArray();
        AcNode p = root;
        int i = 0;
        for (; i < chars.length; i++) {
            char c = chars[i];
            AcNode node = p.child.get(c);
            if (node == null) {
                break;
            }
            p = node;
        }
        return i == chars.length && p.isEndingChar;
    }

    /**
     * 将字符串s中的敏感词（字典树中数据）替换为目标字符
     *
     * @param s
     */
    public String sensitiveWordProcess(String s, char replaceChar) {
        if (s == null || s.length() == 0) {
            return s;
        }
        int n = s.length();
        AcNode p = root;
        String ret = s;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            while (p.child.get(c) == null && p != root) {
                p = p.fail;
            }
            p = p.child.get(c);
            if (p == null) {
                p = root;
            }
            AcNode t = p;
            while (t != root) {
                if (t.isEndingChar) {
                    ret = ret.substring(0, i - t.len + 1) + getMaskChars(replaceChar, t.len) + ret.substring(i + 1);
                }
                t = t.fail;
            }
        }
        return ret;
    }

    private static String getMaskChars(char replaceChar, int i) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < i; j++) {
            sb.append(replaceChar);
        }
        return sb.toString();
    }

    //类似KMP算法里next数组构建
    private void buildFailPoint() {
        Queue<AcNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            AcNode p = queue.poll();
            Map<Character, AcNode> child = p.child;
            for (AcNode pc : child.values()) {
                if (p == root) {
                    pc.fail = root;
                } else {
                    AcNode q = p.fail;
                    while (q != null) {
                        AcNode qc = q.child.get(pc.data);
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }
}

class AcNode {
    char data;
    boolean isEndingChar;
    int len;
    AcNode fail;
    Map<Character, AcNode> child;

    AcNode(char c) {
        child = new HashMap<>();
        data = c;
    }
}
