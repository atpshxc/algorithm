package com.algo.search;

import java.util.HashMap;
import java.util.Map;

public class SundayStringMatch {
    public static void main(String[] args) {
        SundayStringMatch sundayStringMatch = new SundayStringMatch();
        System.out.println(sundayStringMatch.indexOf("8wFtfZ2qg5RgjcqTLJpVwYjySuZHZIwmg7X8sc4oYnGJDbaCczJpKukfkZjLtdaYBkLzpBNQFJihuw5iOzY6TmYWVp5IIg79D8DqYyLjOT8HH7CiKwpVThhUX3dd0vKzUlAplpM5dqjcwgQruPS6qjq9oY7QF8JlbGEXkg8KT8rgfIK7tZstwqsp75hvu0h8iDlWKhCI4skbR6En7j9DKm415fkKnyBShldMgyKOBUfCwiFTjMgx", "gyKOBUfCwiFT"));
    }
    public int indexOf(String s, String t) {
        if (s == null || t == null || t.length() > s.length() || t.length() == 0) {
            return -1;
        }
        Map<Character, Integer> next = buildNext(t);
        int i = 0;
        int j = 0;
        while (j < t.length() && i < s.length()) {
            if (t.charAt(j) == s.charAt(i)) {
                i++;
                j++;
            } else {
                if (i + t.length() >= s.length()) {
                    return -1;
                }
                Integer step = next.get(s.charAt(i + t.length()));
                if (step == null) {
                    i += t.length();
                } else {
                    i += step;
                }
                j = 0;
            }
        }
        return j == t.length() ? i - t.length() : -1;
    }

    private Map<Character, Integer> buildNext(String t) {
        Map<Character, Integer> next = new HashMap<>();
        int length = t.length();
        for (int i = 0; i < length; i++) {
            next.put(t.charAt(i), length - i);
        }
        return next;
    }
}
