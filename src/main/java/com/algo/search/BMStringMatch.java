package com.algo.search;

import java.util.HashMap;
import java.util.Map;

/**
 * BM算法
 */
public class BMStringMatch {
    public static void main(String[] args) {
        BMStringMatch match = new BMStringMatch();
        System.out.println(match.indexOf("BM 算法核心思想是，利用模式串本身的特点，在模式串中某个字符与主串不能匹配的时候，将模式串往后多滑动几位，以此来减少不必要的字符比较，提高匹配的效率。BM 算法构建的规则有两类，坏字符规则和好后缀规则。好后缀规则可以独立于坏字符规则使用。因为坏字符规则的实现比较耗内存，为了节省内存，我们可以只用好后缀规则来实现 BM 算法。", "坏字符规则"));
    }

    public int indexOf(String source, String target) {
        if (source == null || target == null || target.length() > source.length() || target.length() == 0) {
            return -1;
        }
        int i = 0;
        int n = source.length();
        int m = target.length();
        Map<Character, Integer> index = buildIndex(target);
        int[] suffix = new int[m];
        boolean[] prefix = new boolean[m];
        buildSuffixPrefix(target, suffix, prefix);

        while (i <= n - m) {
            int j = m - 1;
            for (; j >= 0; j--) {
                if (target.charAt(j) != source.charAt(i + j)) {
                    break;
                }
            }
            if (j == -1) {
                return i;
            }
            i += Math.max(getStepByBadCharIndex(source, i, index, j), getStepByGoodSuffixIndex(suffix, prefix, j, m));
        }
        return -1;
    }

    private int getStepByGoodSuffixIndex(int[] suffix, boolean[] prefix, int j, int m) {
        //有好后缀
        if (j < m - 1) {
            int len = m - 1 - j;
            if (suffix[len] != -1) {
                return j + 1 - suffix[len];
            } else {
                for (int r = j + 2; r < m; r++) {
                    if (prefix[m - r]) {
                        return r;
                    }
                }
            }
            return m;
        }
        return 0;
    }

    private int getStepByBadCharIndex(String source, int i, Map<Character, Integer> index, int j) {
        int x;
        Integer badCharIndex = index.get(source.charAt(i + j));
        if (badCharIndex == null) {
            x = j + 1;
        } else {
            x = j - badCharIndex;
        }
        return x;
    }

    /*
    suffix下标为后缀子串长度,值是前缀子串最后一个匹配的位置, prefix下标和suffix下标意思一样，当suffix[i]==0,prefix[i]=true
    eg: target == abcabc
        suffix[1] = 2
        suffix[2] = 1
        suffix[3] = 0      prefix[3] = true
        suffix[4] = -1
        suffix[5] = -1
     */
    private void buildSuffixPrefix(String target, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < suffix.length; i++) {
            suffix[i] = -1;
            prefix[i] = false;
        }
        int m = target.length();
        for (int i = 0; i < m - 1; i++) {
            int j = i;
            int k = 0;
            while (j >= 0 && target.charAt(j) == target.charAt(m - k - 1)) {
                suffix[++k] = j--;
            }
            if (j == -1) {
                prefix[i] = true;
            }
        }
    }

    private Map<Character, Integer> buildIndex(String target) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < target.length(); i++) {
            map.put(target.charAt(i), i);
        }
        return map;
    }
}
