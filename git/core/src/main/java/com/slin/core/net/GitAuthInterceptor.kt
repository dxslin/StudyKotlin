package com.slin.core.net

import okhttp3.Interceptor
import okhttp3.Response


/**
 * author: slin
 * date: 2020/9/3
 * description: git登录和认证
 * todo git登录
 */
class GitAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }

}