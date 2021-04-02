package com.slin.study.kotlin.leetcode;

/**
 * author: slin
 * date: 2021/2/25
 * description:正则表达式匹配
 */
public class RegexpMatchTest {

    public boolean isMatch(String s, String p) {
        int index = 0;
        int i = 0;
        for (i = 0; i < p.length() && index < s.length(); i++) {
            char ch = p.charAt(i);

            switch (ch) {
                case '.':
                    if (i < p.length() - 1) {
                        char nextCh = p.charAt(i + 1);
                        if (nextCh == '*') {
                            break;
                        }
                    }
                    // .x
                    index++;
                    break;
                case '*':
                    char previousCh = p.charAt(i - 1);
                    if (previousCh == '.') {

                        if (i < p.length() - 1) {
                            char nextCh = p.charAt(i + 1);
                            while (nextCh != s.charAt(index) && index < s.length()) {
                                index++;
                            }
                        } else {
                            while (index < s.length()) {
                                index++;
                            }
                        }
                    } else {
                        while (s.charAt(index) == previousCh && index < s.length()) {
                            index++;
                        }

                    }


                    break;
                default:
                    if (s.charAt(index) == ch) {
                        index++;
                    } else {
                        return false;
                    }

                    break;
            }


        }


        return i == p.length() - 1;
    }


}
