package com.slin.git.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.slin.core.config.ApplyRetrofitOptions
import com.slin.core.config.CoreConfig
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
import dagger.hilt.android.qualifiers.ApplicationContext
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
        @ApplicationContext context: Context,
        gitUserInfoStorage: GitUserInfoStorage,
        applyRetrofitOptions: ApplyRetrofitOptions,
    ): CoreConfig {
        return CoreConfig(
            application = context as Application,
            baseUrl = Config.BASE_URL,
            httpLogLevel = DefaultConfig.HTTP_LOG_LEVEL,
            customInterceptors = listOf<Interceptor>(
                GitAuthInterceptor(gitUserInfoStorage)
            ),
            applyRetrofitOptions = applyRetrofitOptions,
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
