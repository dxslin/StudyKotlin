package com.slin.git.http.service

import com.slin.git.entity.UserAccessToken
import com.slin.git.http.bean.LoginRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 * author: slin
 * date: 2020/9/4
 * description:
 *
 */
interface LoginService {

    @POST("authorizations")
    @Headers("Accept: application/json")
    suspend fun authorizations(@Body authRequestModel: LoginRequestModel): Response<UserAccessToken>

}
