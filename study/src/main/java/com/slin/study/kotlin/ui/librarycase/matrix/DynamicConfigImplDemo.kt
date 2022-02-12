package com.slin.study.kotlin.ui.librarycase.matrix

import com.tencent.mrs.plugin.IDynamicConfig


/**
 * DynamicConfigImplDemo
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/2/9
 */
class DynamicConfigImplDemo : IDynamicConfig {

    fun isFPSEnable(): Boolean {
        return true
    }

    fun isTraceEnable(): Boolean {
        return true
    }

    fun isMatrixEnable(): Boolean {
        return true
    }

    fun isDumpHprof(): Boolean {
        return false
    }

    override fun get(key: String?, defStr: String?): String? {
        //hook to change default values
        return defStr
    }

    override fun get(key: String, defInt: Int): Int {
        //hook to change default values
        return defInt
    }

    override fun get(key: String, defLong: Long): Long {
        //hook to change default values
        return defLong
    }

    override fun get(key: String, defBool: Boolean): Boolean {
        //hook to change default values
        return defBool
    }

    override fun get(key: String, defFloat: Float): Float {
        //hook to change default values
        return defFloat
    }
}