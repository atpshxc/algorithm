package com.algo.graph;

import java.util.LinkedList;

public class WeightGraph {
    private int size;
    private Vertex[] vertexes;
    private LinkedList<Edge>[] adj;

    public WeightGraph(int size) {
        this.size = size;
        vertexes = new Vertex[size];
        adj = new LinkedList[size];

        for (int i = 0; i < size; i++) {
            adj[i] = new LinkedList();
        }
    }

    public static void main(String[] args) {
        WeightGraph graph = new WeightGraph(7);
        initGraph(graph);
        int[] distanceTab = dijkstra(graph, 0);
        int distance = distanceTab[6];
        System.out.println(distance);
    }

    private static int[] dijkstra(WeightGraph graph, int start) {
        int[] distanceTab = new int[graph.size];
        int[] preVertexTab = new int[graph.size];
        boolean[] visited = new boolean[graph.size];
        LinkedList<Edge> list = new LinkedList<>();
        list.offer(new Edge(start, 0));
        dijkstra(graph, distanceTab, visited, list, preVertexTab);
        return distanceTab;
    }

    private static void dijkstra(WeightGraph graph, int[] distanceTab, boolean[] visited, LinkedList<Edge> list, int[] preVertexTab) {
        while (!list.isEmpty()) {
            Edge e = list.poll();
            int index = e.index;
            if (visited[index]) {
                continue;
            }
            visited[index] = true;
            for (Edge edge : graph.adj[index]) {
                if (visited[edge.index]) {
                    continue;
                }
                if (distanceTab[edge.index] == 0) {
                    preVertexTab[edge.index] = e.index;
                    distanceTab[edge.index] = edge.weight + e.weight;
                } else {
                    int m = Math.min(e.weight, distanceTab[e.index]);
                    if (distanceTab[edge.index] > edge.weight + m) {
                        distanceTab[edge.index] = edge.weight + m;
                        preVertexTab[edge.index] = e.index;
                    }
                }
                list.offer(new Edge(edge.index, edge.weight + e.weight));
            }
        }
    }

    private static void initGraph(WeightGraph graph) {
        graph.vertexes[0] = new Vertex("A");
        graph.vertexes[1] = new Vertex("B");
        graph.vertexes[2] = new Vertex("C");
        graph.vertexes[3] = new Vertex("D");
        graph.vertexes[4] = new Vertex("E");
        graph.vertexes[5] = new Vertex("F");
        graph.vertexes[6] = new Vertex("G");

        graph.adj[0].add(new Edge(1, 5));
        graph.adj[0].add(new Edge(2, 2));
        graph.adj[1].add(new Edge(0, 5));
        graph.adj[1].add(new Edge(3, 1));
        graph.adj[1].add(new Edge(4, 6));
        graph.adj[2].add(new Edge(0, 2));
        graph.adj[2].add(new Edge(3, 6));
        graph.adj[2].add(new Edge(5, 8));
        graph.adj[3].add(new Edge(1, 1));
        graph.adj[3].add(new Edge(2, 6));
        graph.adj[3].add(new Edge(4, 1));
        graph.adj[3].add(new Edge(5, 2));
        graph.adj[4].add(new Edge(1, 6));
        graph.adj[4].add(new Edge(3, 1));
        graph.adj[4].add(new Edge(6, 7));
        graph.adj[5].add(new Edge(2, 8));
        graph.adj[5].add(new Edge(3, 2));
        graph.adj[5].add(new Edge(6, 3));
        graph.adj[6].add(new Edge(4, 7));
        graph.adj[6].add(new Edge(5, 3));

    }

    static class Vertex {
        String data;

        public Vertex(String data) {
            this.data = data;
        }
    }

    static class Edge {
        int index;
        int weight;

        public Edge(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }
    }

}
