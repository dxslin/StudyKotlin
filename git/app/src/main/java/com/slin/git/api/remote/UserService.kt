package com.slin.git.api.remote

import com.slin.git.entity.ReceivedEvent
import com.slin.git.entity.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * author: slin
 * date: 2020/9/16
 * description:
 *
 */

interface UserService {


    @GET("users/{username}/received_events?")
    suspend fun queryReceivedEvents(
        @Path("username") username: String,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int
    ): Response<List<ReceivedEvent>>

    @GET("users/{username}/repos?")
    suspend fun queryRepos(
        @Path("username") username: String,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String
    ): Response<List<Repo>>

}