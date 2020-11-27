package com.slin.git.di

import android.app.Application
import android.content.SharedPreferences
import com.slin.core.config.AppConfig
import com.slin.core.config.ApplyOkHttpOptions
import com.slin.core.config.ApplyRetrofitOptions
import com.slin.core.config.DefaultConfig
import com.slin.core.di.CoreSharePreferencesQualifier
import com.slin.git.api.local.GitUserInfoStorage
import com.slin.git.config.Config
import com.slin.git.net.GitAuthInterceptor
import com.slin.git.net.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * author: slin
 * date: 2020/9/10
 * description: app动态配置项，需要依赖注入的配置在这里
 *
 */

@Module
@InstallIn(ApplicationComponent::class)
object ConfigModule {

    @Provides
    fun provideAppConfig(
        application: Application,
        interceptors: List<Interceptor>,
        applyOkHttpOptions: ApplyOkHttpOptions,
    ): AppConfig {
        return AppConfig(
            application = application,
            baseUrl = Config.BASE_URL,
            httpLogLevel = DefaultConfig.HTTP_LOG_LEVEL,
            customInterceptors = interceptors,
            applyOkHttpOptions = applyOkHttpOptions,
        )
    }

    @Provides
    @Singleton
    fun provideInterceptorList(gitUserInfoStorage: GitUserInfoStorage): List<Interceptor> {
        return listOf<Interceptor>(
            GitAuthInterceptor(gitUserInfoStorage)
        )
    }

    @Provides
    @Singleton
    fun provideGitUserInfoStorage(
        @CoreSharePreferencesQualifier
        sharedPreferences: SharedPreferences,
    ): GitUserInfoStorage {
        return GitUserInfoStorage.getInstance(sharedPreferences)
    }

    @Provides
    fun provideApplyRetrofitOptions(liveDataCallAdapterFactory: LiveDataCallAdapterFactory): ApplyRetrofitOptions {
        return object : ApplyRetrofitOptions {
            override fun apply(builder: Retrofit.Builder) {
                builder.addCallAdapterFactory(liveDataCallAdapterFactory)
            }
        }
    }

}
