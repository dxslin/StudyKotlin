package com.slin.git

import android.app.Application
import com.slin.core.SCore
import com.slin.core.logger.initLogger
import dagger.hilt.android.HiltAndroidApp


/**
 * author: slin
 * date: 2020/9/4
 * description:
 *
 */
@HiltAndroidApp
class SlinGitApplication : Application() {

    companion object {
        lateinit var INSTANCE: SlinGitApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        SCore.init(this)
        SCore.initLogger(BuildConfig.DEBUG)

    }


}

