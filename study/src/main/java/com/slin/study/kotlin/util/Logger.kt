package com.slin.study.kotlin.util

import android.util.Log


/**
 * author: slin
 * date: 2020/8/14
 * description:
 *
 */
object Logger {

    fun log(tag: String, log: String) {
        Log.d(Thread.currentThread().name + "-" + tag, log)
    }


}
