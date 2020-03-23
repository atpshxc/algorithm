package com.algo;

import java.util.Random;

/**
 * 动态规划练习
 */
public class Dp {
    int minDist = Integer.MAX_VALUE;

    //回溯算法
    public void minDistBt(int i, int j, int dist, int n, int[][] m) {
        if (i == n - 1 && j == n - 1) {
            if (dist + m[i][j] < minDist) {
                minDist = dist + m[i][j];
            }
            return;
        }
        if (i < n - 1) {
            minDistBt(i + 1, j, dist + m[i][j], n, m);
        }
        if (j < n - 1) {
            minDistBt(i, j + 1, dist + m[i][j], n, m);
        }
    }

    /*
        二维矩阵最短路径求解， 只能往右或往下移动，求从0，0 到n-1,n-1的最短距离，数组值为距离
     */
    public int minDist(int[][] m) {
        int n = m.length;
        int[][] states = new int[n][n];
        states[0][0] = m[0][0];
        minDist(m, n, 0, 1, states);//往右移动
        minDist(m, n, 1, 0, states);//往右移动
        return states[n - 1][n - 1];
    }

    private void minDist(int[][] m, int n, int row, int col, int[][] states) {
        if (row == n || col == n) {
            return;
        }
        int up = Integer.MAX_VALUE;
        if (row - 1 >= 0) {
            up = states[row - 1][col];
        }
        int left = Integer.MAX_VALUE;
        if (col - 1 >= 0) {
            left = states[row][col - 1];
        }
        states[row][col] = Math.min(up, left) + m[row][col];
        if (row < n) {
            minDist(m, n, row + 1, col, states);
        }
        if (col < n) {
            minDist(m, n, row, col + 1, states);
        }
    }

    //动态规划-状态转移方程; states[i][j] = Math.min(states[i-1][j], states[i][j-1]) + m[i][j]
    public int minDist1(int i, int j, int[][] m, int[][] states) {
        if (i == 0 && j == 0) {
            return m[0][0];
        }
        if (states[i][j] > 0) {
            return states[i][j];
        }
        int up = Integer.MAX_VALUE;
        if (i - 1 >= 0) {
            up = minDist1(i - 1, j, m, states);
        }
        int left = Integer.MAX_VALUE;
        if (j - 1 >= 0) {
            left = minDist1(i, j - 1, m, states);
        }
        states[i][j] = Math.min(up, left) + m[i][j];
        return states[i][j];
    }

    //动态规划-填表法
    public int minDist0(int[][] m) {
        int n = m.length;
        int[][] states = new int[n][n];
        //初始化第一行
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += m[0][i];
            states[0][i] = sum;
        }
        sum = 0;
        //初始化第一列
        for (int i = 0; i < n; i++) {
            sum += m[i][0];
            states[i][0] = sum;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                states[i][j] = Math.min(states[i - 1][j], states[i][j - 1]) + m[i][j];
            }
        }
        return states[n - 1][n - 1];
    }

    /*
    假设我们有几种不同币值的硬币 v1，v2，……，vn（单位是元）。如果我们要支付 w 元，求最少需要多少个硬币。比如，我们有 3 种不同的硬币，
    1 元、3 元、5 元，我们要支付 9 元，最少需要 3 个硬币（3 个 3 元的硬币）。
     */
    //回溯法
    public int coin(int c, int curMoney, int[] coins, int money) {
        if (curMoney == money) {
            return c;
        }
        if (curMoney > money) {
            return Integer.MAX_VALUE;
        }
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < coins.length; i++) {
            int t = coin(c + 1, curMoney + coins[i], coins, money);
            if (t < min) {
                min = t;
            }
        }
        return min;
    }

    //动态规划
    public int coinDp(int[] coins, int money) {
        int[] states = new int[money + 1];
        return coinDp0(coins, money, states);
    }

    private int coinDp0(int[] coins, int money, int[] states) {
        if (money == 0) {
            return 0;
        }
        if (money < 0) {
            return Integer.MAX_VALUE;
        }
        if (states[money] > 0) {
            return states[money];
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int t = coinDp0(coins, money - coins[i], states);
            if (t < min) {
                min = t;
            }
        }
        states[money] = 1 + min;
        return 1 + min;
    }

    /*
        相似度计算：莱文斯坦距离
     */
    //回溯
    public void lwstBt(String s, String t) {
        char[] source = s.toCharArray();
        char[] target = t.toCharArray();
        lwstBt0(0, 0, source, target, 0);
    }

    private void lwstBt0(int i, int j, char[] source, char[] target, int edist) {
        if (i == source.length || j == target.length) {
            if (i < source.length) {
                edist += source.length - i;
            }
            if (j < target.length) {
                edist += target.length - j;
            }
            if (edist < minDist) {
                minDist = edist;
            }
            return;
        }
        if (source[i] == target[j]) {
            lwstBt0(i + 1, j + 1, source, target, edist);
        } else {
            lwstBt0(i + 1, j, source, target, edist + 1);
            lwstBt0(i, j + 1, source, target, edist + 1);
            lwstBt0(i + 1, j + 1, source, target, edist + 1);
        }
    }

    /*
    动态规划-填表法:
        如果source[j] == target[i]
            states[i][j] = states[i-1][j-1]
        否则
            states[i][j] = min(states[i-1][j-1], states[i-1][j] + 1, states[i][j-1] + 1)
     */
    public int lwstDp(String s, String t) {
        char[] source = s.toCharArray();
        char[] target = t.toCharArray();
        int m = source.length;
        int n = target.length;
        int[][] states = new int[m][n];
        if (source[0] == target[0]) {
            states[0][0] = 0;
        } else {
            states[0][0] = 1;
        }
        for (int i = 1; i < n; i++) {
            if (source[0] == target[i]) {
                states[0][i] = i;
            } else {
                states[0][i] = states[0][i - 1] + 1;
            }
        }
        for (int i = 1; i < m; i++) {
            if (source[i] == target[0]) {
                states[i][0] = i;
            } else {
                states[i][0] = states[i - 1][0] + 1;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (source[i] == target[j]) {
                    states[i][j] = states[i - 1][j - 1];
                } else {
                    states[i][j] = Math.min(Math.min(states[i - 1][j - 1] + 1, states[i - 1][j] + 1), states[i][j - 1] + 1);
                }
            }
        }
        return states[m - 1][n - 1];
    }

    /*
    动态规划-状态转移方程
     */
    public int lwstDp1(String s, String t) {
        char[] source = s.toCharArray();
        char[] target = t.toCharArray();
        int m = source.length;
        int n = target.length;
        int[][] states = new int[m][n];
        lwstDp1(m - 1, n - 1, source, target, states);
        return states[m - 1][n - 1];
    }

    private int lwstDp1(int i, int j, char[] s, char[] t, int[][] states) {
        if (i == 0 && j == 0) {
            if (s[i] == t[j]) {
                return 0;
            } else {
                return 1;
            }
        }
        if (i < 0) {
            return states[0][j];
        }
        if (j < 0) {
            return states[i][0];
        }
        if (states[i][j] > 0) {
            return states[i][j];
        }
        if (i - 1 >= 0 && j - 1 >= 0 && s[i] == t[j]) {
            states[i][j] = lwstDp1(i - 1, j - 1, s, t, states);
        } else {
            int a = Integer.MAX_VALUE, b = Integer.MAX_VALUE, c = Integer.MAX_VALUE;
            if (i - 1 >= 0 && j >= 0) {
                a = lwstDp1(i - 1, j - 1, s, t, states) + 1;
            }
            if (i - 1 >= 0) {
                b = lwstDp1(i - 1, j, s, t, states) + 1;
            }
            if (j - 1 >= 0) {
                c = lwstDp1(i, j - 1, s, t, states) + 1;
            }
            states[i][j] = Math.min(Math.min(a, b), c);
        }
        return states[i][j];
    }

    /*
    最长公共子串长度:最长公共子串作为编辑距离中的一种，只允许增加、删除字符两种编辑操作。从名字上，你可能觉得它看起来跟编辑距离没什么关系。
    实际上，从本质上来说，它表征的也是两个字符串之间的相似程度。
     */
    public int lcs(String source, String target) {
        char[] s = source.toCharArray();
        char[] t = target.toCharArray();
        int m = s.length;
        int n = t.length;
        int[][] states = new int[m][n];
        if (s[0] == t[0]) {
            states[0][0] = 1;
        } else {
            states[0][0] = 0;
        }
        //初始化0行
        for (int i = 1; i < n; i++) {
            if (s[0] == t[i]) {
                states[0][i] = 1;
            } else {
                states[0][i] = states[0][i - 1];
            }
        }
        //初始化0列
        for (int i = 1; i < m; i++) {
            if (s[i] == t[0]) {
                states[i][0] = 1;
            } else {
                states[i][0] = states[i - 1][0];
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (s[i] == t[j]) {
                    states[i][j] = states[i - 1][j - 1] + 1;
                } else {
                    states[i][j] = Math.max(Math.max(states[i - 1][j - 1], states[i - 1][j]), states[i][j - 1]);
                }
            }
        }
        return states[m - 1][n - 1];
    }

    /*
  我们有一个数字序列包含 n 个不同的数字，如何求出这个序列中的最长递增子序列长度？比如 2, 9, 3, 6, 5, 1, 7 这样一组数字序列，
  它的最长递增子序列就是 2, 3, 5, 7，所以最长递增子序列的长度是 4。
   */
    //回溯
    public int ls(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            int t = ls(nums, i, nums[i], 1);
            if (t > max) {
                max = t;
            }
        }
        return max;
    }

    /*
    动态规划
    递推公式:
    a[0...i] 的最长子序列为: a[i] 之前所有比它小的元素中子序列长度最大的 + 1
     */

    public int lsdp(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        int n = nums.length;
        int[] states = new int[n];
        states[0] = 1;
        for (int i = 1; i < n; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && states[j] > max) {
                    max = states[j];
                }
            }
            states[i] = max + 1;
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (states[i] > max) {
                max = states[i];
            }
        }
        return max;
    }

    private int ls(int[] nums, int i, int cur, int count) {
        if (i == nums.length) {
            return count;
        }
        if (nums[i] > cur) {
            return Math.max(ls(nums, i + 1, nums[i], count + 1), ls(nums, i + 1, cur, count));
        } else {
            return ls(nums, i + 1, cur, count);
        }
    }

    public static void main(String[] args) {
        Dp dp = new Dp();
        String source = "aidu";
        String[] dict = {"apple", "facebook", "one", "google", "alibaba", "tencent", "baidu"};
        int min = Integer.MAX_VALUE;
        String res = null;
        //实现单词纠错
        for (String target : dict) {
            int i = dp.lwstDp(source, target);
            if (i < min) {
                min = i;
                res = target;
            }
        }
        System.out.println(res);
        int n = 120;
        int[] nums = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            nums[i] = random.nextInt(5000);
        }
        System.out.println(dp.ls(nums));
        System.out.println(dp.lsdp(nums));
    }
}
