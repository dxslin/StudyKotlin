package com.slin.study.kotlin.util

import com.slin.core.logger.loge
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

object ReflectUtils {

    @Suppress("SpreadOperator")
    fun getMethod(obj: Any, methodName: String, vararg params: Any): Method? {
        val paramTypes = Array(params.size) { params[it]::class.java }
        return getMethod(obj::class.java, methodName, *paramTypes)
    }

    fun getMethod(clazz: Class<*>, methodName: String, vararg paramTypes: Class<*>): Method? {
        return try {
            clazz.getMethod(methodName, *paramTypes)
        } catch (e: NoSuchMethodException) {
            loge(e) { "" }
            null
        } catch (e: SecurityException) {
            loge(e) { "" }
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> invokeMethod(obj: Any, methodName: String, vararg params: Any): T? {
        val method = getMethod(obj, methodName, params)
        return try {
            method?.invoke(obj, *params) as T
        } catch (e: IllegalAccessException) {
            loge(e) { "" }
            null
        } catch (e: IllegalArgumentException) {
            loge(e) { "" }
            null
        } catch (e: InvocationTargetException) {
            loge(e) { "" }
            null
        }
    }

    fun getField(clazz: Class<*>, fieldName: String): Field? {
        return try {
            clazz.getField(fieldName)
        } catch (e: NoSuchFieldException) {
            loge(e) { "" }
            null
        }
    }

    fun getField(obj: Any, methodName: String): Field? {
        return getField(obj::class.java, methodName)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getFieldValue(obj: Any, methodName: String): T? {
        val field = getField(obj::class.java, methodName)
        field?.isAccessible = true
        return try {
            field?.get(obj) as T
        } catch (e: IllegalAccessException) {
            loge(e) { "" }
            null
        } catch (e: IllegalArgumentException) {
            loge(e) { "" }
            null
        }
    }

}