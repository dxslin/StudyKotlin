package com.slin.core.di

import okhttp3.OkHttpClient
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.provider
import retrofit2.Retrofit


/**
 * author: slin
 * date: 2020/9/2
 * description: Http 服务注入module
 *
 */

const val HTTP_CLIENT_MODULE_TAG = "http_client_module_tag"

val httpClientModule = DI.Module(HTTP_CLIENT_MODULE_TAG) {

    bind<OkHttpClient.Builder>() with provider {
        OkHttpClient.Builder()
    }

    bind<Retrofit.Builder>() with provider {
        Retrofit.Builder()
    }


}


