package com.algo;

/**
 * 假设正则表达式中只包含“*”和“?”这两种通配符，并且对这两个通配符的语义稍微做些改变，其中，“*”匹配任意多个（大于等于 0 个）任意字符，
 * “?”匹配零个或者一个任意字符
 */
public class Pattern {
    private int plen;
    private char[] patterns;
    private boolean matched;

    public Pattern(String pattern) {
        patterns = pattern.toCharArray();
        plen = pattern.length();
    }

    public static void main(String[] args) {
        Pattern pattern = new Pattern("*2?");
        System.out.println(pattern.match("2388"));
    }

    public boolean match(String text) {
        int tlen = text.length();
        char[] texts = text.toCharArray();
        matched = false;
        match(0, 0, texts, tlen);
        return matched;
    }

    private void match(int textIndex, int patternIndex, char[] texts, int tlen) {
        if (matched) {
            return;
        }
        if (patternIndex == plen) {
            if (textIndex == tlen) {
                matched = true;
            }
            return;
        }
        if (patterns[patternIndex] == '*') {
            for (int i = 0; i <= tlen - textIndex; i++) {
                match(textIndex + i, patternIndex + 1, texts, tlen);
            }
        } else if (patterns[patternIndex] == '?') {
            match(textIndex, patternIndex + 1, texts, tlen);
            match(textIndex + 1, patternIndex + 1, texts, tlen);
        } else if (textIndex < tlen && texts[textIndex] == patterns[patternIndex]) {
            match(textIndex + 1, patternIndex + 1, texts, tlen);
        }
    }
}
