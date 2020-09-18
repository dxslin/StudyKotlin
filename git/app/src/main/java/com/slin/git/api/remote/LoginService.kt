package com.slin.git.api.remote

import com.slin.git.api.bean.LoginRequestModel
import com.slin.git.entity.UserAccessToken
import com.slin.git.entity.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
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

    @GET("user")
    suspend fun fetchUserOwner(): Response<UserInfo>

}
