package com.slin.study.kotlin.reflect;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FinalModifierReflect {
    @Test
    public void test01() throws Exception {
        setFinalStatic(Constant.class.getDeclaredField("i1"), 11);
        System.out.println(Constant.i1);

        setFinalStatic(Constant.class.getDeclaredField("i2"), 22);
        System.out.println(Constant.i2);

        setFinalStatic(Constant.class.getDeclaredField("s1"), "change1");
        System.out.println(Constant.s1);

        setFinalStatic(Constant.class.getDeclaredField("s2"), "change2");
        System.out.println(Constant.s2);

        System.out.println("----------------");

        setFinalStatic(CC.class.getDeclaredField("i1"), 11);
        System.out.println(CC.i1);

        setFinalStatic(CC.class.getDeclaredField("i2"), 22);
        System.out.println(CC.i2);

        setFinalStatic(CC.class.getDeclaredField("i3"), 33);
        System.out.println(CC.i3);

        setFinalStatic(CC.class.getDeclaredField("s1"), "change1");
        System.out.println(CC.s1);

        setFinalStatic(CC.class.getDeclaredField("s2"), "change2");
        System.out.println(CC.s2);

        setFinalStatic(CC.class.getDeclaredField("s3"), "change3");
        System.out.println(CC.s3);

    }

    private void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    interface Constant {
        int i1 = 1;
        Integer i2 = 1;
        String s1 = "s1";
        String s2 = new String("s2");
    }

    static class CC {
        private static final int i1 = 1;
        private static final Integer i2 = 1;
        private static final String s1 = "s1";
        private static final String s2 = new String("s2");
        private static Integer i3 = 1;
        private static String s3 = "s3";
    }

}
