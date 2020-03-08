package com.algo.tree;

import java.util.LinkedList;
import java.util.Random;

public class AVLTree<E extends Comparable<E>> {
    private class Node<E extends Comparable<E>> {
        private E value;
        private int height;
        private int depth;
        private Node left;
        private Node right;

        public Node(E value) {
            this.value = value;
            height = 1;
            depth = 1;
        }
    }

    private Node root;
    private int size;
    private int height = 1;

    public int getDepth(Node node) {
        if (node == null || node.value == null) {
            return 0;
        }
        return node.depth;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isBalanced() {
        return isBalanced(this.root);
    }

    public boolean isBalanced(Node node) {
        if (node.value == null) {
            return true;
        }
        int balanceFactory = getBalanceFactory(node);
        if (balanceFactory > 1) {
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }

    private int getBalanceFactory(Node node) {
        if (node.value == null) {
            return 0;
        }
        return getDepth(node.left) - getDepth(node.right);
    }

    //TODO
    public void remove(E e) {
        Node node = root;
        Node parent;
        while (node != null) {
            if (node.value.compareTo(e) == 0) {

            }
            if (node.value.compareTo(e) >= 0) {

            }
        }
    }

    public void add(E e) {
        root = add(root, e);
    }

    private Node add(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }
        boolean isLeaf = node.left == null && node.right == null;
        if (node.value == null || node.value.compareTo(e) >= 0) {
            node.left = add(node.left, e);
        } else if (node.value == null || node.value.compareTo(e) < 0) {
            node.right = add(node.right, e);
        }
        node.depth = Math.max(getDepth(node.left), getDepth(node.right)) + 1;

        int balanceFactory = getBalanceFactory(node);
        boolean rotate = false;
        if (balanceFactory > 1 && getBalanceFactory(node.left) >= 0) {
            //LL右旋
            node = rotateRight(node);
            rotate = true;
        } else if (balanceFactory < -1 && getBalanceFactory(node.right) <= 0) {
            //RR左旋
            node = rotateLeft(node);
            rotate = true;
        } else if (balanceFactory > 1 && getBalanceFactory(node.left) < 0) {
            //LR -> LL后右旋
            node.left = rotateLeft(node.left);
            node = rotateRight(node);
            rotate = true;
        } else if (balanceFactory < -1 && getBalanceFactory(node.right) > 0) {
            //RL -> RR后左旋
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            rotate = true;
        }
        if (isLeaf && !rotate) {
            height++;
        }
        return node;
    }

    private void adjustChildHeight(Node node) {
        if (node.left != null) {
            node.left.height = node.height - 1;
            adjustChildHeight(node.left);
        } else if (node.left == null && node.height >= 2) {
            node.left = new Node(null);
            node.left.height = node.height - 1;
            adjustChildHeight(node.left);
        }
        if (node.right != null) {
            node.right.height = node.height - 1;
            adjustChildHeight(node.right);
        } else if (node.right == null && node.height >= 2) {
            node.right = new Node(null);
            node.right.height = node.height - 1;
            adjustChildHeight(node.right);
        }
    }

    private Node rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        node = right;

        node.left.depth = Math.max(getDepth(node.left.left), getDepth(node.left.right)) + 1;
        node.depth = Math.max(getDepth(node.left), getDepth(node.right)) + 1;
        return node;
    }

    private Node rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;
        node = left;

        node.right.depth = Math.max(getDepth(node.right.left), getDepth(node.right.right)) + 1;
        node.depth = Math.max(getDepth(node.left), getDepth(node.right)) + 1;
        return node;
    }

    private Node FALG = new Node(null);

    @Override
    public String toString() {
        root.height = height / 2;
        adjustChildHeight(root);
        LinkedList<Node> list = new LinkedList<>();
        list.add(root);
        list.add(FALG);
        return toString(list);
    }

    public String toString(LinkedList<Node> list) {
        if (list.size() == 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Node node = list.removeFirst();
        sb.append(getPrefixChars(node.height));
        while (node != FALG) {
            sb.append(getNodeValue(node)).append(getGapChars(node.height));
            if (node.left != null) {
                list.addLast(node.left);
            }
            if (node.right != null) {
                list.addLast(node.right);
            }
            node = list.removeFirst();
        }
        list.addLast(FALG);
        sb.append("\n");
        return sb.append(toString(list)).toString();
    }

    private String  getNodeValue(Node node) {
        return node.value == null ? " " : node.value.toString();
    }

    private String getGapChars(int height) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.pow(2, height) - 1; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    private String getPrefixChars(int height) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.pow(2, height - 1) - 1; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        Random random = new Random(147);
        for (int i = 0; i< 15; i++) {
            avlTree.add(random.nextInt(1000));
        }
        System.out.println(avlTree);
    }
}
