package com.slin.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton


/**
 * author: slin
 * date: 2020/9/3
 * description: 数据层注入
 *
 */

const val DEFAULT_SHARE_PREFERENCES_TAG = "score_share_preferences"

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    @SCoreSharePreferencesQualifier
    fun provideSharedPreferences(@SCoreApplicationQualifier application: Application): SharedPreferences {
        return application.getSharedPreferences(DEFAULT_SHARE_PREFERENCES_TAG, Context.MODE_PRIVATE)
    }


}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SCoreSharePreferencesQualifier
