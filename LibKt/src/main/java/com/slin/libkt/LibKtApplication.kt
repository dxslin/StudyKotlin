package com.slin.libkt

import android.app.Application
import androidx.multidex.MultiDex
import com.slin.core.SCore
import com.slin.core.logger.initLogger


/**
 * author: slin
 * date: 2021/1/28
 * description:
 *
 */
class LibKtApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)

        SCore.init(this)
        SCore.initLogger(BuildConfig.DEBUG)

    }

}