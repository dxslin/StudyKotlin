package com.slin.study.kotlin.util

import android.content.Context
import android.os.Handler
import android.os.Looper

/**
 * author: slin
 * date: 2021/7/12
 * description:
 *
 */
class CrashHandler : Thread.UncaughtExceptionHandler {

    private val TAG = CrashHandler::class.java.simpleName

    private lateinit var context: Context


    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            CrashHandler()
        }
    }

    fun init(context: Context) {
        this.context = context
        Thread.setDefaultUncaughtExceptionHandler(this)
        Handler(Looper.getMainLooper()).post {
            while (true) {
                try {
                    Logger.log(TAG, "Looper.loop() start")
                    Looper.loop()
                    Logger.log(TAG, "Looper.loop() end")
                } catch (e: Throwable) {
                    Logger.log(TAG, "exception: ${e.message}")
                    e.printStackTrace()
                }
            }
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
//        e.printStackTrace()
        Logger.log(TAG, "程序崩溃了，555")

//        Handler(Looper.getMainLooper()).post {
//            while (true) {
//                try {
//                    Logger.log(TAG, "Looper.loop() start")
//                    Looper.loop()
//                    Logger.log(TAG, "Looper.loop() end")
//                } catch (e: Throwable) {
//                }
//            }
//        }

    }

}