package com.algo;

import java.util.Random;

public class Bag01 {
    int max;

    /**
     * 0-1 背包问题 回溯算法复杂度 2^n
     * 我们有一个背包，背包总的承载重量是 Wkg。现在我们有 n 个物品，每个物品的重量不等，并且不可分割。
     * 我们现在期望选择几件物品，装载到背包中。在不超过背包所能装载重量的前提下，如何让背包中物品的总重量最大？
     *
     * @param i           当前放第几个物品
     * @param curWeight   当前背包里物品总重量
     * @param totalWeight 背包承重量
     * @param items       物品重量数组
     * @param n           物品个数
     */
    public void put(int i, int curWeight, int totalWeight, int[] items, int n) {
        if (i == n || curWeight == totalWeight) {
            if (curWeight > max) {
                max = curWeight;
            }
            return;
        }
        put(i + 1, curWeight, totalWeight, items, n);//不放第i个物品
        if (curWeight + items[i] <= totalWeight) {
            put(i + 1, curWeight + items[i], totalWeight, items, n);
        }
    }

    /*
    动态规划解法：时间复杂度 items.length * totalWeight
    states【第几个物品】【放入或不放入背包后当前背包的重量】= true表示这是一中存放选择
     */
    public int knapsack(int[] items, int totalWeight) {
        int n = items.length;
        boolean[][] states = new boolean[n][totalWeight + 1];
        states[0][0] = true;//第0个物品不放入背包
        if (items[0] <= totalWeight) {
            states[0][items[0]] = true;//第0个物品放入背包
        }
        for (int i = 1; i < n; i++) {
            //第i个物品不放入背包
            for (int j = 0; j <= totalWeight; j++) {
                if (states[i - 1][j]) {
                    states[i][j] = true;
                }
            }
            //第i个物品放入背包
            for (int j = 0; j <= totalWeight - items[i]; j++) {
                if (states[i - 1][j]) {
                    states[i][j + items[i]] = true;
                }
            }
        }
        for (int i = totalWeight; i >= 0; i--) {
            if (states[n - 1][i]) {
                return i;
            }
        }
        return 0;
    }

    /*
    动态规划优化版
     */
    public int knapsack1(int[] items, int totalWeight) {
        int n = items.length;
        boolean[] states = new boolean[totalWeight + 1];
        states[0] = true;
        if (items[0] <= totalWeight) {
            states[items[0]] = true;
        }
        for (int i = 1; i < n; i++) {
            for (int j = totalWeight - items[i]; j >= 0; j--) {
                if (states[j]) {
                    states[j + items[i]] = true;
                }
            }
        }
        for (int i = totalWeight; i >= 0; i--) {
            if (states[i]) {
                return i;
            }
        }
        return 0;
    }

    /*
     0-1 背包问题升级版，增加物品价值
     */
    int maxValue;

    public void put1(int i, int curWeight, int curValue, int totalWeight, int[] weight, int[] values, int n) {
        if (i == n || curWeight == totalWeight) {
            if (curValue > maxValue) {
                maxValue = curValue;
            }
            return;
        }
        put1(i + 1, curWeight, curValue, totalWeight, weight, values, n);
        if (curWeight + weight[i] <= totalWeight) {
            put1(i + 1, curWeight + weight[i], curValue + values[i], totalWeight, weight, values, n);
        }
    }

    /*
    背包问题升级版对应动态规划解决方案
     */
    public int knapsack2(int[] weight, int[] values, int totalWeight) {
        int n = weight.length;
        int[][] states = new int[n][totalWeight + 1];//值为物品的value
        states[0][0] = 0;
        if (weight[0] <= totalWeight) {
            states[0][weight[0]] = values[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= totalWeight; j++) {
                states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= totalWeight - weight[i]; j++) {
                int v = states[i - 1][j] + values[i];
                if (v > states[i][weight[i] + j]) {
                    states[i][weight[i] + j] = v;
                }
            }
        }
        int max = 0;
        for (int i = 0; i <= totalWeight; i++) {
            if (states[n - 1][i] > max) {
                max = states[n - 1][i];
            }
        }
        return max;
    }

    /*
    背包问题升级版对应动态规划优化版解决方案
     */
    public int knapsack3(int[] weight, int[] values, int totalWeight) {
        int n = weight.length;
        int[] states = new int[totalWeight + 1];//值为物品的value
        states[0] = 0;
        if (weight[0] <= totalWeight) {
            states[weight[0]] = values[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = totalWeight - weight[i]; j >= 0; j--) {
                int v = states[j] + values[i];
                if (v > states[weight[i] + j]) {
                    states[weight[i] + j] = v;
                }
            }
        }
        return states[totalWeight];
    }

    public static void main(String[] args) {
        Bag01 bag01 = new Bag01();
        int n = 170;
        Random random = new Random();
        int[] weight = new int[n];
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            weight[i] = 300 + random.nextInt(300);
            values[i] = random.nextInt(300);
        }

        bag01.put1(0, 0, 0, 10 * n, weight, values, n);
        System.out.println(bag01.maxValue);
        System.out.println(bag01.knapsack2(weight, values, 10 * n));
        System.out.println(bag01.knapsack3(weight, values, 10 * n));
    }
}
