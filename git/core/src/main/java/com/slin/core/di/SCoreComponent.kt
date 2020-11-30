package com.slin.git.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.slin.core.config.CoreConfig
import com.slin.core.di.CoreComponentDependencies
import com.slin.core.di.CoreSharePreferencesQualifier
import com.slin.core.di.ImageOkHttpClientQualifier
import com.slin.core.di.OkHttpClientQualifier
import com.slin.core.image.ImageLoader
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit


/**
 * author: slin
 * date: 2020/11/30
 * description: component，组件
 * 全局共享对象，在没有注入点的地方使用这个来获取全局对象
 *
 * 如果自定义注入点，需要自己创建component
 *
 */

@Component(dependencies = [CoreComponentDependencies::class])
interface SCoreComponent {

    fun inject(application: Application)


    fun coreConfig(): CoreConfig

    fun retrofit(): Retrofit

    @OkHttpClientQualifier
    fun okHttpClient(): OkHttpClient

    @ImageOkHttpClientQualifier
    fun imageOkHttpClient(): OkHttpClient

    fun imageLoader(): ImageLoader

    @CoreSharePreferencesQualifier
    fun coreSharedPreferences(): SharedPreferences

    fun gson(): Gson

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun coreDependencies(coreComponentDependencies: CoreComponentDependencies): Builder
        fun build(): SCoreComponent
    }


}
