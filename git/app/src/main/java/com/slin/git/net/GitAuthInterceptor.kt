package com.slin.git.net

import android.util.Base64
import com.slin.git.api.local.GitUserInfoStorage
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * author: slin
 * date: 2020/9/3
 * description: git登录和认证
 *
 */
class GitAuthInterceptor(private val userInfoStorage: GitUserInfoStorage) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val accessToken = getAuthorization()

        if (accessToken.isNotEmpty()) {
            val url = request.url.toString()
            request = request.newBuilder()
                .addHeader("Authorization", accessToken)
                .url(url)
                .build()
        }

        return chain.proceed(request)
    }

    private fun getAuthorization(): String {
        val accessToken = userInfoStorage.token
        val username = userInfoStorage.username
        val password = userInfoStorage.password

        if (accessToken.isBlank()) {
            val basicIsEmpty = username.isBlank() || password.isBlank()
            return if (basicIsEmpty) {
                ""
            } else {
                "$username:$password".let {
                    "basic " + Base64.encodeToString(it.toByteArray(), Base64.NO_WRAP)
                }
            }
        }
        return "token $accessToken"
    }
}