package com.slin.study.kotlin;

import org.junit.Test;

public class PrintChinese {

    static String[] unit = {"十", "百", "千", "万"};
    static String[] number = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    @Test
    public void test() {
        System.out.println("Hello World!");

        int value = 4001;

        StringBuilder sb = new StringBuilder();
        if (value >= 10000) {
            sb.append(chinese(value / 10000))
                    .append("万")
                    .append(chinese(value % 10000));
        } else {
            sb.append(chinese(value));
        }

        System.out.println(sb.toString());


    }

    private static String chinese(int value) {
        StringBuilder sb = new StringBuilder();
        int nIndex = 0;
        // 1000 1001 1111
        while (value > 10) {
            int i = value % 10;
            if (i > 0) {
                sb.append(number[i]);
            }
            value /= 10;
            // 400
            if (value % 10 != 0) {  //40
                sb.append(unit[nIndex++]);
            } else {
                nIndex++;
                if (sb.length() > 0 && !number[0].equals(String.valueOf(sb.charAt(sb.length() - 1)))) {
                    sb.append(number[0]);
                }
            }
        }
        if (value > 0) {
            sb.append(number[value]);
        }
        return sb.reverse().toString();
    }

}
