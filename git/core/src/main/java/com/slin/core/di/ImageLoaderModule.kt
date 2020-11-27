package com.slin.core.di

import com.slin.core.image.ImageLoader
import com.slin.core.image.ImageLoaderStrategy
import com.slin.core.image.impl.GlideImageLoaderStrategy
import com.slin.core.image.impl.ImageConfigImpl
import com.slin.core.logger.logd
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton


/**
 * author: slin
 * date: 2020/9/14
 * description:
 *
 */


@Module
@InstallIn(ApplicationComponent::class)
object ImageLoaderModule {

    @Provides
    @Singleton
    fun provideImageLoader(strategy: ImageLoaderStrategy<ImageConfigImpl>): ImageLoader {
        return ImageLoader(strategy)
    }

    @Provides
    @Singleton
    fun provideImageLoaderStrategy(): ImageLoaderStrategy<ImageConfigImpl> {
        return GlideImageLoaderStrategy()
    }

    @Provides
    @Singleton
    @ImageOkHttpClientQualifier
    fun provideOkHttpClient(
        builder: OkHttpClient.Builder,
        @ImageHttpLoggingInterceptorQualifier
        httpLoggingInterceptor: HttpLoggingInterceptor,
        dispatcher: Dispatcher,
    ): OkHttpClient {
        return builder.dispatcher(dispatcher)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    }

    @Singleton
    @Provides
    @ImageHttpLoggingInterceptorQualifier
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                logd { "glide: $message" }
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageOkHttpClientQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageHttpLoggingInterceptorQualifier

