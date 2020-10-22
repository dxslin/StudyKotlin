package com.slin.git.api.remote

import androidx.lifecycle.LiveData
import com.slin.core.net.Results
import com.slin.git.api.entity.ReceivedEvent
import com.slin.git.api.entity.Repo
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
    fun queryReceivedEvents(
        @Path("username") username: String,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int
    ): LiveData<Results<List<ReceivedEvent>>>

    @GET("users/{username}/repos?")
    suspend fun queryRepos(
        @Path("username") username: String,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String
    ): Results<List<Repo>>

}