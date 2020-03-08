package com.algo.search;

public class KMPStringSearch {
    public static void main(String[] args) {
        KMPStringSearch match = new KMPStringSearch();
        System.out.println(match.indexOf("BM 算法核心思想是，利用模式串本身的特点，在模式串中某个字符与主串不能匹配的时候，将模式串往后多滑动几位，以此来减少不必要的字符比较，提高匹配的效率。BM 算法构建的规则有两类，坏字符规则和好后缀规则。好后缀规则可以独立于坏字符规则使用。因为坏字符规则的实现比较耗内存，为了节省内存，我们可以只用好后缀规则来实现 BM 算法。", "坏字符规则"));
    }
    public int indexOf(String source, String target) {
        if (source == null || target == null || target.length() > source.length() || target.length() == 0) {
            return -1;
        }
        int[] next = buildNext1(target);
        int j = 0;
        for (int i = 0; i < source.length(); i++) {
            //j>0表示有最好前缀
            while (j > 0 && source.charAt(i) != target.charAt(j)) {
                j = next[j - 1] + 1;
            }
            if (source.charAt(i) == target.charAt(j)) {
                j++;
            }
            if (j == target.length()) {
                return i - target.length() + 1;
            }
        }
        return -1;
    }

    //简单但是效率不高的算法
    private int[] buildNext(String target) {
        int m = target.length();
        int[] next = new int[m];
        next[0] = -1;
        for (int i = 1; i < m; i++) {
            int j = (i + 1) >> 1;
            if ((i & 1) == 1) {
                j++;
            }
            int k = -1;
            for (; j <= i; j++, k++) {
                if (target.charAt(k + 1) != target.charAt(j)) {
                    break;
                }
            }
            next[i] = k;
        }
        return next;
    }
    /*
    利用动态规划思想。如果next[i-1]=k-1, 且target[k]==target[i],则next[i]=k,
    如果target[k]！=target[i] 则target[i]的值一定在0到k-1之间，问题转化为target[0到k-1]子串的最长匹配前缀，
    而target[0到k-1]子串的最长匹配前缀 == next[k-1]
     */
    private int[] buildNext1(String target) {
        int m = target.length();
        int[] next = new int[m];
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < m; i++) {
            while (k != -1 && target.charAt(k + 1) != target.charAt(i)) {
                k = next[k];
            }
            if (target.charAt(k + 1) == target.charAt(i)) {
                k++;
            }
            next[i] = k;
        }
        return next;
    }
}
