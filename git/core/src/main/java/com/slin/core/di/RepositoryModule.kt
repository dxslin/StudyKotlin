package com.slin.core.di

import android.content.Context
import android.content.SharedPreferences
import com.slin.core.CoreApplication
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


/**
 * author: slin
 * date: 2020/9/3
 * description: 数据层注入
 *
 */
const val REPOSITORY_MODULE_TAG = "repository_module_tag"

const val DEFAULT_SHARE_PREFERENCES_TAG = "core_share_preferences"

val repositoryModule = DI.Module(REPOSITORY_MODULE_TAG) {

    bind<SharedPreferences>(DEFAULT_SHARE_PREFERENCES_TAG) with singleton {
        instance<CoreApplication>()
            .getSharedPreferences(DEFAULT_SHARE_PREFERENCES_TAG, Context.MODE_PRIVATE)
    }

}

