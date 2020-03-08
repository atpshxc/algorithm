package com.algo.offer;

import java.util.LinkedList;

public class Graph {
    private int v;
    private LinkedList<Integer>[] adj;

    Graph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList();
        }
    }

    public void addEdge(int start, int target) {
        adj[start].add(target);
        adj[target].add(start);
    }

    public void bfs(int start, int target) {
        if (start == target) {
            return;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(start);
        boolean[] visited = new boolean[v];
        visited[start] = true;
        int[] pre = new int[v];
        while (!queue.isEmpty()) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); i++) {
                int q = adj[w].get(i);
                if (visited[q]) {
                    continue;
                }
                pre[q] = w;
                if (q == target) {
                    print(pre, start, target);
                    return;
                }
                visited[q] = true;
                queue.offer(q);
            }
        }
    }

    boolean found = false;

    public void dfs(int start, int target) {
        if (start == target) {
            return;
        }
        boolean[] visited = new boolean[v];
        visited[start] = true;
        int[] pre = new int[v];
        dfs(start, target, pre, visited);
        print(pre, start, target);
    }

    private void dfs(int start, int target, int[] pre, boolean[] visited) {
        if (found) {
            return;
        }

        for (int i = 0; i < adj[start].size(); i++) {
            int t = adj[start].get(i);
            if (!visited[t]) {
                visited[t] = true;
                pre[t] = start;
                if (t == target) {
                    return;
                }
                dfs(t, target, pre, visited);
            }
        }
    }

    private void print(int[] pre, int start, int target) {
        if (target != start) {
            print(pre, start, pre[target]);
        }
        System.out.print(target + "->");
    }

    public static void main(String[] args) {
        Graph g = new Graph(9);
        g.addEdge(0, 2);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(1, 6);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 5);
        g.addEdge(4, 5);
        g.addEdge(7, 5);
        g.addEdge(7, 6);
        g.addEdge(5, 8);

//        g.bfs(0, 8);
        g.dfs(0, 8);
    }
}
