package com.algo.tree;

public class BinarySearchTree {
    private Node root;

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(4);
        tree.insert(3);
        tree.insert(6);
        tree.insert(5);
        tree.insert(7);
        tree.delete(4);
    }
    public void insert(int data) {
        if (root == null) {
            root = new Node(data);
        } else {
            Node p = root;
            while (p != null) {
                if (data > p.data) {
                    if (p.right == null) {
                        p.right = new Node(data);
                        return;
                    }
                    p = p.right;
                } else {
                    if (p.left == null) {
                        p.left = new Node(data);
                        return;
                    }
                    p = p.left;
                }
            }
        }
    }

    public Node find(int data) {
        if (root == null) {
            return null;
        }
        Node p = root;
        while (p != null) {
            if (data < p.data) {
                p = p.left;
            } else if (data > p.data) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    public Node findP(int data) {
        if (root == null) {
            return null;
        }
        Node p = root;
        Node pp = null;
        while (p != null) {
            if (data < p.data) {
                pp = p;
                p = p.left;
            } else if (data > p.data) {
                pp = p;
                p = p.right;
            } else {
                return pp;
            }
        }
        return null;
    }

    public void delete(int data) {
        if (root == null) {
            return;
        }
        Node p = root;
        Node pp = null;
        while (p != null) {
            if (data < p.data) {
                pp = p;
                p = p.left;
            } else if (data > p.data) {
                pp = p;
                p = p.right;
            } else {
                break;
            }
        }
        if (p == null) {
            return;
        }
        if (p.left != null && p.right != null) {
            Node t = p.right;
            pp = p;
            while (t.left != null) {
                pp = t;
                t = t.left;
            }
            p.data = t.data;
            p = t;
        }
        //when delete node is root and root.right is null
        if (pp == null) {
            root = p.left;
            return;
        }
        if (pp.left == p) {
            pp.left = p.left;
        } else {
            pp.right = p.right;
        }
    }

    static class Node {
        Node left;
        Node right;
        int data;

        Node(int data) {
            this.data = data;
        }
    }
}
