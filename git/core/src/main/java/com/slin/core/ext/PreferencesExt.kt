package com.slin.core.ext

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * author: slin
 * date: 2020/9/3
 * description: SharePreferences扩展函数
 * 定义一个SharedPreferences的代理扩展方法，返回一个ReadWriteProperty实现，
 * 这样就可以直接代理SharePreferences的读取和储存，还可以在中间统一加入自己的缓存逻辑
 *
 */
private inline fun <T> SharedPreferences.delegate(
    key: String?,
    defaultValue: T,
    crossinline setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor,
    crossinline getter: SharedPreferences.(String, T) -> T
) = object : ReadWriteProperty<Any, T> {
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        edit().setter(key ?: property.name, value).apply()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getter(key ?: property.name, defaultValue)!!
    }

}


fun SharedPreferences.int(key: String? = null, defaultValue: Int = 0): ReadWriteProperty<Any, Int> {
    return delegate(key, defaultValue, SharedPreferences.Editor::putInt, SharedPreferences::getInt)
}

fun SharedPreferences.long(
    key: String? = null,
    defaultValue: Long = 0
): ReadWriteProperty<Any, Long> {
    return delegate(
        key,
        defaultValue,
        SharedPreferences.Editor::putLong,
        SharedPreferences::getLong
    )
}

fun SharedPreferences.float(
    key: String? = null,
    defaultValue: Float = 0f
): ReadWriteProperty<Any, Float> {
    return delegate(
        key,
        defaultValue,
        SharedPreferences.Editor::putFloat,
        SharedPreferences::getFloat
    )
}

fun SharedPreferences.boolean(
    key: String? = null,
    defaultValue: Boolean = false
): ReadWriteProperty<Any, Boolean> {
    return delegate(
        key,
        defaultValue,
        SharedPreferences.Editor::putBoolean,
        SharedPreferences::getBoolean
    )
}

fun SharedPreferences.stringSet(
    key: String? = null,
    defaultValue: Set<String> = emptySet()
): ReadWriteProperty<Any, Set<String>> {
    return delegate(
        key,
        defaultValue,
        SharedPreferences.Editor::putStringSet,
        { it, set -> getStringSet(it, set) as Set<String> })
}


fun SharedPreferences.string(
    key: String? = null,
    defaultValue: String = ""
): ReadWriteProperty<Any, String> {
    return delegate(
        key,
        defaultValue,
        SharedPreferences.Editor::putString,
        { str1, str2 -> getString(str1, str2) as String })
}
