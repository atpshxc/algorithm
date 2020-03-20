package com.algo.tree;

public class Bag01 {
    int max;

    /**
     *
     * 0-1 背包问题
     * 我们有一个背包，背包总的承载重量是 Wkg。现在我们有 n 个物品，每个物品的重量不等，并且不可分割。
     * 我们现在期望选择几件物品，装载到背包中。在不超过背包所能装载重量的前提下，如何让背包中物品的总重量最大？
     * @param i 当前放第几个物品
     * @param curWeight 当前背包里物品总重量
     * @param totalWeight 背包承重量
     * @param items 物品重量数组
     * @param n 物品个数
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

    public static void main(String[] args) {
        Bag01 bag01 = new Bag01();
        int[] items = {3,5,11,13,15,9,10,21,11,8};

        bag01.put(0, 0, 100, items, 10);
        System.out.println(bag01.max);
    }
}
