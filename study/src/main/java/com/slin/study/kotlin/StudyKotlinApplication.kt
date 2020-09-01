package com.slin.study.kotlin

import android.app.Application
import com.slin.study.kotlin.util.ThemeHelper
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

class StudyKotlinApplication : Application(), KodeinAware {

    override val kodein: Kodein
        get() = Kodein.lazy {
            bind<StudyKotlinApplication>() with singleton { this@StudyKotlinApplication }
            import(androidModule(this@StudyKotlinApplication))
            import(androidXModule(this@StudyKotlinApplication))


        }


    companion object {
        lateinit var INSTANCE: StudyKotlinApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        ThemeHelper.init(this)

    }

}
