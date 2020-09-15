package com.slin.core.ext

import android.content.Context
import com.slin.core.CoreApplication
import com.slin.core.config.AppConfig


/**
 * author: slin
 * date: 2020/9/14
 * description:
 *
 */
fun Context.getAppConfig(): AppConfig {
    return (applicationContext as CoreApplication).appConfig
}