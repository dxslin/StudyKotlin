package com.slin.core.logger

import android.util.Log
import timber.log.Timber


/**
 * author: slin
 * date: 2020/9/4
 * description:
 *
 */

class CrashReportTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }
        CrashReportHelper.log(priority, tag, message, t)
        t?.let {
            when (priority) {
                Log.WARN -> CrashReportHelper.logWarn(message, t)
                Log.ERROR -> CrashReportHelper.logError(message, t)
            }
        }
    }
}

private class CrashReportHelper private constructor() {



    init {
        throw AssertionError("No instances")
    }

    companion object {
        private val TAG: String? = CrashReportHelper::class.simpleName

        fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            Log.println(priority, TAG, message)
            t?.printStackTrace()
        }

        fun logWarn(message: String, t: Throwable?) {

        }

        fun logError(message: String, t: Throwable?) {

        }

    }

}