package com.slin.study.kotlin

import android.app.Application
import com.slin.study.kotlin.util.ThemeHelper


/**
 * author: slin
 * date: 2020/8/4
 * description:
 *
 */

class StudyKotlinApplication : Application() {

    companion object {
        lateinit var INSTANCE: StudyKotlinApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        ThemeHelper.init(this)

    }

}
