package com.slin.core.logger

import android.app.Application
import com.slin.core.functional.Supplier
import timber.log.Timber


/**
 * author: slin
 * date: 2020/9/4
 * description: 日志类，使用Timber打印日志
 *
 */
fun Application.initLogger(isDebug: Boolean) {
    if (isDebug) {
        Timber.plant(Timber.DebugTree())
    } else {
        Timber.plant(CrashReportTree())
    }
    log { "Initialize logger successful, isDebug: $isDebug" }
}

inline fun log(supplier: Supplier<String>) = logd(supplier)

inline fun logd(supplier: Supplier<String>) = Timber.d(supplier())

inline fun logi(supplier: Supplier<String>) = Timber.i(supplier())

inline fun logw(supplier: Supplier<String>) = Timber.w(supplier())

inline fun loge(t: Throwable? = null, supplier: Supplier<String>) =
    if (t == null) {
        Timber.e(supplier())
    } else {
        Timber.e(t, supplier())
    }
