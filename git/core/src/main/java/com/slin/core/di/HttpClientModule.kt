package com.slin.core.di

import com.slin.core.net.GitAuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.*
import org.kodein.di.android.BuildConfig
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


/**
 * author: slin
 * date: 2020/9/2
 * description: Http 服务注入module
 *
 */

const val HTTP_CLIENT_MODULE_TAG = "http_client_module_tag"

const val TIME_OUT_SECONDS = 10L
const val BASE_URL = "https://api.github.com/"


val httpClientModule = DI.Module(HTTP_CLIENT_MODULE_TAG) {

    bind<OkHttpClient.Builder>() with provider {
        OkHttpClient.Builder()
    }

    bind<Retrofit.Builder>() with provider {
        Retrofit.Builder()
    }

    bind<HttpLoggingInterceptor>() with provider {
        HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    bind<GitAuthInterceptor>() with provider {
        GitAuthInterceptor()
    }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
            .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(instance<HttpLoggingInterceptor>())
            .addInterceptor(instance<GitAuthInterceptor>())
            .build()
    }

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
            .baseUrl(BASE_URL)
            .client(instance())
            .build()
    }

}


