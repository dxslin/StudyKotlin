package com.slin.study.kotlin.ui.jetpack.hlit

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext


/**
 * author: slin
 * date: 2020/11/20
 * description: 依赖注入module，使用provides注解
 * 这种方式使用单例模式，可以提供第三方类的实例，比如Gson、Retrofit、OkHttp等
 *
 */
@Module
@InstallIn(ActivityComponent::class)
object ProvidesModule {

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideGlideRequestManager(@ActivityContext context: Context): RequestManager {
        return Glide.with(context)
    }

}

