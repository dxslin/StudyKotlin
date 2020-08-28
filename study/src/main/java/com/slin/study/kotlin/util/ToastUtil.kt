package com.slin.study.kotlin.util

import android.app.Activity
import android.content.Context
import android.widget.Toast


/**
 * author: slin
 * date: 2020/7/6
 * description:
 *
 */

fun toast(context: Context, msg: CharSequence, duration: Int) {
    Toast.makeText(context, msg, duration).show()
}

fun Activity.toast(msg: CharSequence) {
    toast(this, msg, Toast.LENGTH_SHORT)
}
