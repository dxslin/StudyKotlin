package com.slin.git.api.remote

import com.slin.core.net.Results
import com.slin.git.api.entity.Issue
import com.slin.git.api.entity.Repo
import com.slin.git.api.entity.SearchResult
import com.slin.git.api.entity.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


/**
 * author: slin
 * date: 2020/10/22
 * description:
 *
 */

interface SearchService {


    //https://api.github.com/search/users?q=Ray&sort=followers&order=desc
    @GET("search/users")
    suspend fun searchUsers(
        @Query(value = "q", encoded = true) query: String?,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("page") page: Int
    ): Results<SearchResult<UserInfo>>

    //https://api.github.com/search/repositories?q=StudyKotlin&sort=followers&order=desc
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query(value = "q", encoded = true) query: String?,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("page") page: Int
    ): Results<SearchResult<Repo>>

    //https://api.github.com/search/issues?sort=created&page=1&q=user:ThirtyDegreesRay+state:open&order=desc
    @GET("search/issues")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    suspend fun searchIssues(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Query(value = "q", encoded = true) query: String?,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("page") page: Int
    ): Response<SearchResult<Issue>>

}
