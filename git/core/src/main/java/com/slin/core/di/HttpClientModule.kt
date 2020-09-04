package com.slin.core.di

import com.google.gson.Gson
import com.slin.core.BuildConfig
import com.slin.core.net.GitAuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
            .baseUrl(BASE_URL)
            .client(instance())
            .addConverterFactory(instance())
            .build()
    }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
            .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(instance<HttpLoggingInterceptor>())
            .addInterceptor(instance<GitAuthInterceptor>())
            .build()
    }

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


    bind<Gson>() with singleton {
        Gson()
    }

    bind<GsonConverterFactory>() with provider {
        GsonConverterFactory.create(instance())
    }

}


