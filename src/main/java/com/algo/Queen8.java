package com.algo;

public class Queen8 {
    class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) {
        int[] result = new int[8];
        cal8Queen(result, 0);
    }

    private static void cal8Queen(int[] result, int row) {
        if (row == 8) {
            print(result);
            return;
        }
        for (int col = 0; col < result.length; col++) {
            if (isOk(result, row, col)) {
                result[row] = col;
                cal8Queen(result, row + 1);
            }
        }
    }

    private static boolean isOk(int[] result, int row, int col) {
        int leftUp = col - 1;
        int rightUp = col + 1;
        for (int i = row - 1; i >= 0; i--) {
            if (result[i] == col || result[i] == leftUp || result[i] == rightUp) {
                return false;
            }
            leftUp--;
            rightUp++;
        }
        return true;
    }
    static int count = 0;
    private static void print(int[] result) {
        count++;
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                if (result[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println(count+ "=================================");
    }
}
