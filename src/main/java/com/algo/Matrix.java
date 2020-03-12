package com.algo;

import java.util.Random;

public class Matrix {
    int[][] multi(int[][] a, int[][] b) {
        int[][] res = new int[a.length][a[0].length];
        multi(a, b, a.length - 1, res);
        return res;
    }

    private void multi(int[][] a, int[][] b, int n, int[][] res) {
        if (n > 1) {
            multi(a, b, n - 1, res);
            for (int i = 0; i <= n - 1; i++) {
                for (int j = 0; j <= n - 1; j++) {
                    res[i][j] += a[i][n] * b[n][j];
                }
            }
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
                    res[i][n] += a[i][j] * b[j][n];
                }
            }
            for (int i = 0; i <= n - 1; i++) {
                for (int j = 0; j <= n; j++) {
                    res[n][i] += a[n][j] * b[j][i];
                }
            }
            return;
        }
        res[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0];
        res[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1];
        res[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0];
        res[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1];
    }

    /*
    暴力求解和上面方法性能差不多
     */
    public void multi(int[][] a, int[][] b, int[][] res, int len) {
        int i, j, k;
        for (i = 0; i < len; ++i) {
            for (j = 0; j < len; ++j) {
                res[i][j] = 0;
                for (k = 0; k < len; ++k) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        Matrix matrix = new Matrix();
        int n = 1000;
        int[][] res = new int[n][n];
        int[][] a = new int[n][n];
        int[][] b = new int[n][n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = random.nextInt(20);
                b[i][j] = random.nextInt(20);
            }
        }
        long start = System.currentTimeMillis();
        res = matrix.multi(a, b);
        System.out.println(System.currentTimeMillis() - start);
//        print(res);
        start = System.currentTimeMillis();
        matrix.multi(a, b, res, n);
        System.out.println(System.currentTimeMillis() - start);
//        print(res);
    }

    private static void print(int[][] res) {
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
    }
}
