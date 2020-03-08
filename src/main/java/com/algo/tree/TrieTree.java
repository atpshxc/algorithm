package com.algo.tree;

import java.util.*;

public class TrieTree {
    private class Node implements Comparable<Node> {
        private Character value;
        private boolean isStringsEndChar;

        public Node(Character c) {
            value = c;
        }

        @Override
        public String toString() {
            return String.valueOf(value) + ", " + isStringsEndChar;
        }

        public void setValue(Character c) {
            value = c;
        }

        public void setStringsEndChar(boolean isStringsEndChar) {
            this.isStringsEndChar = isStringsEndChar;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public int compareTo(Node o) {
            return value.compareTo(o.value);
        }
    }

    private Map<Node, Map> root;

    public TrieTree() {
        root = new HashMap<>();
    }

    public void insert(String s) {
        if (s == null) {
            return;
        }
        Map<Node, Map> m = root;
        Node node = new Node(null);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            node.setValue(c);
            Map<Node, Map> map = m.get(node);
            if (map == null) {
                map = new HashMap<>();
                m.put(new Node(c), map);
            }
            if (i == s.length() - 1) {
                Node key = findKeyNode(m.keySet(), node);
                key.setStringsEndChar(true);
            }
            m = map;
        }
    }

    public boolean remove(String s) {
        if (!find(s)) {
            return false;
        }
        Node node = new Node(null);
        Map<Node, Map> m = root;
        for (int i = 0; i < s.length(); i++) {
            node.setValue(s.charAt(i));
            Map map = m.get(node);
            if (map != null && map.size() == 1) {
                m.remove(node);
                break;
            }
            if (i == s.length() - 1) {
                Node keyNode = findKeyNode(m.keySet(), node);
                keyNode.setStringsEndChar(false);
                if (map == null || map.size() == 0) {
                    m.remove(node);
                }
            }
            m = map;
        }
        return true;
    }

    public List<String> listByPrefix(String prefix) {
        Node node = new Node(null);
        Map<Node, Map> m = root;
        for (int i = 0; i < prefix.length(); i++) {
            node.setValue(prefix.charAt(i));
            Map map = m.get(node);
            if (map == null || map.isEmpty()) {
                Node keyNode = findKeyNode(m.keySet(), node);
                List list = new ArrayList<>();
                if (keyNode != null && keyNode.isStringsEndChar) {
                    list.add(prefix);
                }
                return list;
            }
            m = map;
        }
        return getStrings(prefix, m);
    }

    public List<String> listSort() {
        List<String> list = new ArrayList<>();
        root.keySet().stream().sorted().forEach(n -> {
            list.addAll(getStrings(String.valueOf(n.value), root.get(n)));
        });
        return list;
    }

    private List<String> getStrings(String prefix, Map<Node, Map> m) {
        List<String> result = new ArrayList<>();
        if (m == null || m.isEmpty()) {
            result.add(prefix);
            return result;
        }
        m.keySet().stream().sorted().forEach(node -> {
            StringBuilder sb = new StringBuilder(prefix);
            sb.append(node.value);
            result.addAll(getStrings(sb.toString(), m.get(node)));
        });
        return result;
    }

    public boolean find(String s) {
        Node node = new Node(null);
        Map<Node, Map> m = root;
        for (int i = 0; i < s.length(); i++) {
            node.setValue(s.charAt(i));
            Map map = m.get(node);
            if (map == null || map.isEmpty() || i == s.length() - 1) {
                break;
            }
            m = map;
        }
        Node key = findKeyNode(m.keySet(), node);
        if (key != null && key.isStringsEndChar) {
            return true;
        }
        return false;
    }

    private Node findKeyNode(Set<Node> set, Node node) {
        for (Node n : set) {
            if (n.equals(node)) {
                return n;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public static void main(String[] args) {
        System.out.println(Long.MAX_VALUE);
        TrieTree trieTree = new TrieTree();
        trieTree.insert("12");
        trieTree.insert("11");
        trieTree.insert("2");
        trieTree.insert("1");
        trieTree.insert("21");
        trieTree.insert("124");
        trieTree.insert("41");
        trieTree.insert("575");
//        System.out.println(trieTree.listByPrefix("abk"));
//        sensitiveWordProcess();
        System.out.println(trieTree.listSort());
    }

    private static void sensitiveWordProcess() {
        TrieTree trieTree = new TrieTree();
        trieTree.insert("麻痹");
        trieTree.insert("妈的");
        trieTree.insert("日");
        trieTree.insert("草");
        trieTree.insert("操");
        String s = "抵扣可麻痹的，上课的妈的，累死了日，操五金草";
        int p1 = 0, p2 = 0;
        while (p1 < s.length()) {
            if (trieTree.find(s.substring(p1, p2 + 1))) {
                s = s.substring(0, p1) + getMaskChar(p2 - p1 + 1) + s.substring(p2 + 1);
                p1 = p2 + 1;
            }
            p2++;
            if (p2 == s.length()) {
                p1++;
                p2 = p1;
            }
        }
        System.out.println(s);
    }

    private static String getMaskChar(int i) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < i; j++) {
            sb.append("*");
        }
        return sb.toString();
    }
}
