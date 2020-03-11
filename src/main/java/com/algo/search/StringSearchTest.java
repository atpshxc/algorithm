package com.algo.search;

import java.util.Random;

public class StringSearchTest {
    private static String s = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static void main(String[] args) throws InterruptedException {
        int count = 10000;
        String[] sources = buildRandomStrings(count, 30, 5000);
        String[] targets = buildRandomStrings(count, 3, 20);

        Thread.sleep(100);

        SundayStringMatch sundayStringMatch = new SundayStringMatch();
        BMStringMatch bmStringMatch = new BMStringMatch();
        KMPStringSearch kmpStringSearch = new KMPStringSearch();

        long start = System.currentTimeMillis();
        int k = 0;
        for (int i = 0; i < count; i++) {
            int index = sundayStringMatch.indexOf(sources[i], targets[i]);
            if (index >= 0) {
                System.out.println(sources[i] + " ," + targets[i]);
                k++;
            }
        }
        System.out.println("SundayStringMatch cost: " + (System.currentTimeMillis() - start) + " k=" + k);

        Thread.sleep(100);
        start = System.currentTimeMillis();
        k = 0;
        for (int i = 0; i < count; i++) {
            int index = bmStringMatch.indexOf(sources[i], targets[i]);
            if (index >= 0) {
                k++;
            }
        }
        System.out.println("BmStringMatch cost: " + (System.currentTimeMillis() - start) + " k=" + k);

        Thread.sleep(100);
        start = System.currentTimeMillis();
        k = 0;
        for (int i = 0; i < count; i++) {
            int index = kmpStringSearch.indexOf(sources[i], targets[i]);
            if (index >= 0) {
                k++;
            }
        }
        System.out.println("KmpStringSearch cost: " + (System.currentTimeMillis() - start) + " k=" + k);
    }

    private static String[] buildRandomStrings(int count, int min, int max) {
        Random random = new Random();
        int bound = s.length();
        String[] res = new String[count];
        for (int i = 0; i < count; i++) {
            StringBuilder sb = new StringBuilder();
            int len = min + random.nextInt(max);
            for (int j = 0; j < len; j++) {
                sb.append(s.charAt(random.nextInt(bound)));
            }
            res[i] = sb.toString();
        }
        return res;
    }
}
