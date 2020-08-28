package com.slin.study.kotlin.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;

/**
 * author: slin
 * date: 2020-02-03
 * description:
 */
public class TestSupport {


    public static void postponeComputation(long delay, final Runnable runnable) {
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runnable.run();
                        cancel();
                    }
                }, delay);
    }

    private void test() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 1000);
    }

    interface XX<T> {
        T xx();
    }

    class YY implements XX<Void> {

        @Override
        public Void xx() {
            return null;
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("Slin");
        //Kotlin中的函数类型就对应于`FunctionN`接口，其中N表示参数个数
        CollectionsKt.forEach(list, new Function1<String, Unit>() {
            @Override
            public Unit invoke(String s) {
                System.out.println(s);
                //在Java中使用Unit返回值时需要返回`Unit.INSTANCE`
                return Unit.INSTANCE;
            }
        });
    }

}
