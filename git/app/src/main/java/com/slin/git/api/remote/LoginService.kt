package com.slin.git.api.remote

import com.slin.core.net.Results
import com.slin.git.api.bean.LoginRequestModel
import com.slin.git.entity.UserAccessToken
import com.slin.git.entity.UserInfo
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
    suspend fun authorizations(@Body authRequestModel: LoginRequestModel): Results<UserAccessToken>

    @GET("user")
    suspend fun fetchUserOwner(): Results<UserInfo>

}
