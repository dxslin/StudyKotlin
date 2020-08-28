package com.slin.study.kotlin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author: slin
 * date: 2020/5/29
 * description:
 */
public class JavaExampleUnitTest {

    private List<String> strList = new ArrayList<>();

    @Before
    public void initArray() {
        System.out.println("init:");
        strList.clear();
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            strList.add(String.valueOf(i));
        }
        System.out.println();
    }

    //使用for移除元素会导致遍历的时候少遍历一个
    @Test
    public void testForRemove() {
        System.out.println("for remove: ");
        for (int i = 0; i < strList.size(); i++) {
            System.out.print(strList.get(i) + " ");
            if (i == 1) {
                strList.remove(strList.get(i));
            }
        }
        System.out.println();
    }

    //使用foreach无法移除元素
    @Test
    public void testForeachRemove() {
        System.out.println("for remove: ");
        int index = 0;

        for (String str : strList) {
            System.out.print(str + " ");
            if (index == 1) {
                strList.remove(str);
            }
            index++;
        }
        System.out.println();
    }

    //使用Iterator移除没有问题
    @Test
    public void testIteratorRemove() {
        Iterator<String> iterator = strList.iterator();
        int index = 0;

        System.out.println("iterator remove: ");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
            if (index == 1) {
                iterator.remove();
            }
            index++;
        }
        System.out.println();
    }

    @After
    public void testAfter() {
        System.out.println("check: ");
        for (String str : strList) {
            System.out.print(str + " ");
        }
    }

}
