package com.slin.core.di

import android.content.SharedPreferences
import com.slin.core.config.CoreConfig
import com.slin.core.image.ImageLoader
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit


/**
 * author: slin
 * date: 2020/11/27
 * description:
 *
 */
@EntryPoint
@InstallIn(ApplicationComponent::class)
interface CoreComponentDependencies {

    fun coreConfig(): CoreConfig

    fun retrofit(): Retrofit

    @OkHttpClientQualifier
    fun okHttpClient(): OkHttpClient

    fun imageLoader(): ImageLoader

    @CoreSharePreferencesQualifier
    fun coreSharedPreferences(): SharedPreferences


}

