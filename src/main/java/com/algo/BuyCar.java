package com.algo;

/*
动态规划练习：
淘宝的“双十一”购物节有各种促销活动，比如“满 200 元减 50 元”。假设你女朋友的购物车中有 n 个（n>100）
想买的商品，她希望从里面选几个，在凑够满减条件的前提下，让选出来的商品价格总和最大程度地接近满减条件（200 元），
这样就可以极大限度地“薅羊毛”
 */
public class BuyCar {
    public static void main(String[] args) {
        BuyCar car = new BuyCar();
        int[] goods = {12, 30, 31, 90, 31, 32, 27, 50, 46, 55};
        car.selectGoods(goods, 10, 200);
    }

    public void selectGoods(int[] goods, int n, int m) {
        boolean[][] states = new boolean[n][m * 3 + 1];
        states[0][0] = true;
        if (goods[0] <= 3 * m) {
            states[0][goods[0]] = true;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= 3 * m; ++j) {
                // 不购买第i个商品
                if (states[i - 1][j] == true) states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= 3 * m - goods[i]; j++) {
                if (states[i - 1][j]) {
                    states[i][j + goods[i]] = true;
                }
            }
        }

        int j;
        //求大于等于m的合计商品价格最小值
        for (j = m; j <= 3 * m; j++) {
            if (states[n - 1][j]) {
                break;
            }
        }
        if (j == 3 * m) return;
        //打印选择的商品
        for (int i = n - 1; i >= 1; i--) {
            if (j - goods[i] >= 0 && states[i - 1][j - goods[i]]) {
                System.out.print(goods[i] + " ");
                j -= goods[i];
            }
        }
        if (j != 0) {
            System.out.print(goods[0]);
        }
    }
}
