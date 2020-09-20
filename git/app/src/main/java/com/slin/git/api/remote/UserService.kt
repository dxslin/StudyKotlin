package com.slin.git.api.remote

import androidx.lifecycle.LiveData
import com.slin.core.net.Results
import com.slin.git.entity.ReceivedEvent
import com.slin.git.entity.Repo
import retrofit2.Response
import retrofit2.SkipCallbackExecutor
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

    @SkipCallbackExecutor
    @GET("users/{username}/received_events?")
    fun queryReceivedEvents(
        @Path("username") username: String,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int
    ): LiveData<Results<List<ReceivedEvent>>>

    @GET("users/{username}/repos?")
    fun queryRepos(
        @Path("username") username: String,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String
    ): Response<List<Repo>>

}