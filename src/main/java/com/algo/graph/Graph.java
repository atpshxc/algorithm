package com.algo.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private int size;
    private Vertex[] vertxes;
    private LinkedList<Integer>[] adj;

    public Graph(int size) {
        this.size = size;
        vertxes = new Vertex[size];
        adj = new LinkedList[size];

        for (int i = 0; i < size; i++) {
            vertxes[i] = new Vertex(i);
            adj[i] = new LinkedList();
        }
    }

    public void dfs(int start, boolean[] visited) {
        Vertex vertx = vertxes[start];
        visited[start] = true;
        System.out.println(vertx.data);
        for (int i : adj[start]) {
            if (!visited[i]) {
                dfs(i, visited);
            }
        }
    }

    public void bfs(int start, boolean[] visited, LinkedList<Integer> queue) {
        queue.offer(start);
        while (!queue.isEmpty()) {
            int i = queue.poll();
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            System.out.println(vertxes[i].data);
            for (int k : adj[i]) {
                if (!visited[k]) {
                    queue.offer(k);
                }
            }
        }
    }

    public List<Integer> minPath(int start, boolean[] visited, LinkedList<Integer> queue, LinkedList<List<Integer>> result, int target) {
        queue.offer(start);
        while (!queue.isEmpty()) {
            int i = queue.poll();
            if (visited[i]) {
                continue;
            }
            List<Integer> list = result.poll();
            list.add(vertxes[i].data);
            visited[i] = true;
//            if (vertxes[i].data == target) {
//                return list;
//            }
            for (int k : adj[i]) {
                if (!visited[k]) {
                    if (vertxes[k].data == target) {
                        list.add(vertxes[k].data);
                        return list;
                    }
                    queue.offer(k);
                    List l = new ArrayList<>();
//                    l.add(k);
                    l.addAll(list);
                    result.offer(l);
                }
            }
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        Graph graph = new Graph(7);
        graph.adj[0].add(1);
        graph.adj[0].add(2);

        graph.adj[1].add(3);
        graph.adj[1].add(4);

        graph.adj[2].add(3);
        graph.adj[2].add(5);

        graph.adj[3].add(4);
        graph.adj[3].add(5);

        graph.adj[4].add(6);

        graph.adj[5].add(6);


        System.out.println("图的深度优先遍历：");

        graph.dfs(0, new boolean[graph.size]);

        System.out.println("图的广度优先遍历：");

        graph.bfs(0, new boolean[graph.size], new LinkedList<>());

        System.out.println("图的最短路径从0-6：");

        LinkedList<List<Integer>> result = new LinkedList<>();
        result.offer(new ArrayList<>());
        System.out.println(graph.minPath(0, new boolean[graph.size], new LinkedList<>(), result, 6));
    }

    class Vertex {
        int data;

        public Vertex(int data) {
            this.data = data;
        }
    }

}
