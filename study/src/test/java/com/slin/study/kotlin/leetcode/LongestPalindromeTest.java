package com.slin.study.kotlin.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * author: slin
 * date: 2021/2/25
 * description: 最长会问子串
 */
public class LongestPalindromeTest {


    @Test
    public void test() {
        System.out.println(longestPalindrome1("aacabdkacaa"));
    }

//    public String longestPalindrome(String s) {
//        byte[] bytes = s.getBytes();
//        Map<Byte, Integer> map = new HashMap<>();
//        int start = 0;
//        int end = 0;
//
//        for (int i = 0; i < bytes.length; i++) {
//            Byte b = bytes[i];
//            Integer index = map.get(b);
//            if (index != null){
//                if(end - start < i - index){
//                    start = index;
//                    end = i;
//                }
//            }
//            map.put(b, i);
//        }
//        return s.substring(start, end + 1);
//
//    }

    public String longestPalindrome1(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        String ans = "";
        for (int k = 0; k < n; ++k) {
            for (int i = 0; i + k < n; ++i) {
                int j = i + k;
                if (k == 0) {
                    dp[i][j] = true;
                } else if (k == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
                }
                if (dp[i][j] && k + 1 > ans.length()) {
                    ans = s.substring(i, i + k + 1);

                }
                System.out.println(s.substring(i, i + k + 1) + " " + dp[i][j] + " " + i + " " + j);
            }
        }
        for (int i = 0; i < dp.length; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        return ans;
    }


}
