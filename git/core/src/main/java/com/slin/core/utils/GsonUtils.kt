package com.slin.core.utils

import com.google.gson.Gson
import com.slin.core.CoreApplication
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance


/**
 * author: slin
 * date: 2020/9/16
 * description: json 转换
 *
 */
object GsonUtils : DIAware {

    override val di: DI = CoreApplication.instance.di

    val INSTANCE: Gson by instance<Gson>()

}

fun <T> T.toJson(): String {
    return GsonUtils.INSTANCE.toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    return GsonUtils.INSTANCE.fromJson(this, T::class.java)
}
