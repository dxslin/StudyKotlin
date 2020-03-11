package com.slin.study.kotlin.support;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author: slin
 * date: 2020/2/13
 * description:可空性
 */
public class CollectionUtils {

    public static List<String> uppercaseAll(List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, items.get(i).toUpperCase());
        }
        return items;
    }

    public interface FileContentProcessor {
        void processContents(File path, byte[] binaryContents, List<String> textContents);
    }

    public interface DataParser<T> {
        void parseData(String input, List<T> output, List<String> errors);
    }

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        System.out.println(integers instanceof List);
        System.out.println(integers instanceof List<?>);
//        System.out.println(integers instanceof List<String>);

        //协变
        List<? extends Food> foods = new ArrayList<>();
        List<Apple> apples = new ArrayList<>();
        foods = apples;     //协变
        foods.add(null);
        //只能添加null，不能添加Food及其子类
//        foods.add(new Apple());

        //逆变
        List<? super Fruit> fruitList = new ArrayList<>();
        List<Food> foodList = new ArrayList<>();
        fruitList = foodList;   //逆变
        fruitList.add(new Apple());
        fruitList.add(new Fruit());
        //只能添加Fruit及其子类
//        fruitList.add(new Food())

    }

    static class Food {

    }

    static class Fruit extends Food {

    }

    static class Apple extends Fruit {

    }

    static class Beef extends Food {

    }


}
