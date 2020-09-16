package com.slin.git.di

import com.slin.git.stroage.remote.LoginService
import com.slin.git.stroage.remote.UserService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit


/**
 * author: slin
 * date: 2020/9/16
 * description: http接口依赖注入module
 *
 */
const val API_SERVICE_MODULE_TAG = "api_service_module_tag"

val apiServiceModule = DI.Module(API_SERVICE_MODULE_TAG) {

    bind<LoginService>() with singleton {
        instance<Retrofit>().create(LoginService::class.java)
    }

    bind<UserService>() with singleton {
        instance<Retrofit>().create(UserService::class.java)
    }

}
