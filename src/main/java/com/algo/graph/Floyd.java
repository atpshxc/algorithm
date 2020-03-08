package com.algo.graph;

public class Floyd {
    private static int INF = Integer.MAX_VALUE;

    public static void floyd(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    if (matrix[j][i] == INF || matrix[i][k] == INF) {
                        continue;
                    }
                    matrix[j][k] = Math.min(matrix[j][k], matrix[j][i] + matrix[i][k]);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {0, 5, 2, INF, INF, INF, INF},
                {5, 0, INF, 1, 6, INF, INF},
                {2, INF, 0, 6, INF, 8, INF},
                {INF, 1, 6, 0, 1, 2, INF},
                {INF, 6, INF, 1, 0, INF, 7},
                {INF, INF, 8, 2, INF, 0, 3},
                {INF, INF, INF, INF, 7, 3, 0}
        };
        Floyd.floyd(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%3d", matrix[i][j]);
            }
            System.out.printf("\n");
        }
    }
}
