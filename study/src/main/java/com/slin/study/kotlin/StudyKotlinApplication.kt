package com.slin.study.kotlin

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.slin.study.kotlin.ui.librarycase.kodein.moduleUser
import com.slin.study.kotlin.ui.librarycase.matrix.MatrixUtil
import com.slin.study.kotlin.util.CrashHandler
import com.slin.study.kotlin.util.Logger
import com.slin.study.kotlin.util.ThemeHelper
import dagger.hilt.android.HiltAndroidApp
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton


/**
 * author: slin
 * date: 2020/8/4
 * description:
 *
 */

@HiltAndroidApp
class StudyKotlinApplication : Application(), KodeinAware, Configuration.Provider {

    override val kodein: Kodein
        get() = Kodein.lazy {
            bind<StudyKotlinApplication>() with singleton { this@StudyKotlinApplication }
            import(androidModule(this@StudyKotlinApplication))
            import(androidXModule(this@StudyKotlinApplication))

            import(moduleUser)
        }


    companion object {
        lateinit var INSTANCE: StudyKotlinApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        ThemeHelper.init(this)
        CrashHandler.instance.init(this)

        MatrixUtil.init(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()
    }

    override fun onLowMemory() {
        Logger.log("TouchEventActivity", "onLowMemory")
        super.onLowMemory()
    }

    override fun onTerminate() {
        Logger.log("TouchEventActivity", "onTerminate")
        super.onTerminate()
    }


}
