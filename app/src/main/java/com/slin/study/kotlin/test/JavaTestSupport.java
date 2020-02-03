package com.slin.study.kotlin.test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author: slin
 * date: 2020-02-03
 * description:
 */
public class JavaTestSupport {


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
}
