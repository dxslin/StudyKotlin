package com.slin.core.utils

import android.content.Context
import android.widget.Toast


/**
 * author: slin
 * date: 2020/8/24
 * description:
 *
 */

fun Context.dp2px(value: Float): Float {
    return value * resources.displayMetrics.density + 0.5f
}

fun Context.sp2px(value: Float): Float {
    return value * resources.displayMetrics.scaledDensity + 0.5f
}

fun Context.screenWidth() = resources.displayMetrics.widthPixels

fun Context.screenHeight() = resources.displayMetrics.heightPixels

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, msg, duration).show()