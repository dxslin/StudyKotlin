package com.slin.git.ui.home.data

import androidx.paging.*
import com.slin.core.net.Results
import com.slin.core.repository.CoreRepository
import com.slin.core.repository.IRemoteDataSource
import com.slin.git.api.arguments.OneArgPage
import com.slin.git.api.arguments.START_PAGE_NUM
import com.slin.git.api.entity.ReceivedEvent
import com.slin.git.api.remote.UserService
import com.slin.git.config.PAGING_REMOTE_INIT_SIZE
import com.slin.git.config.PAGING_REMOTE_PAGE_SIZE
import com.slin.git.ext.getOrAwaitValue
import com.slin.git.manager.UserManager
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * author: slin
 * date: 2020/9/16
 * description: 首页仓库
 *
 */
@ActivityRetainedScoped
class HomeRepository @Inject constructor(private val userService: UserService) : CoreRepository() {

    fun queryReceivedEvents(
        perPage: Int = PAGING_REMOTE_PAGE_SIZE,
    ): Flow<PagingData<ReceivedEvent>> {

        val username = UserManager.requireUserName()
        if (username.isBlank()) {
            return flow { }
        }
        val pageWithArgs = OneArgPage(0, username)

        return Pager(
            PagingConfig(
                pageSize = perPage,
                initialLoadSize = PAGING_REMOTE_INIT_SIZE,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            pageWithArgs
        ) {
            HomeRemoteDataSource(userService)
        }.flow
    }

}

class HomeRemoteDataSource(private val userService: UserService) :
    PagingSource<OneArgPage<String>, ReceivedEvent>(), IRemoteDataSource {

    private suspend fun queryReceivedEvents(
        username: String,
        pageIndex: Int,
        perPage: Int
    ): Results<List<ReceivedEvent>> {

//        val results = userService.queryRepos(username, pageIndex, pageIndex, "")
//        log{"queryReceivedEvents: ${results}"}

        return userService.queryReceivedEvents(username, pageIndex, perPage).getOrAwaitValue()
    }

    override suspend fun load(params: LoadParams<OneArgPage<String>>): LoadResult<OneArgPage<String>, ReceivedEvent> {
        val results = queryReceivedEvents(
            params.key?.arg.orEmpty(),
            params.key?.page ?: START_PAGE_NUM,
            params.loadSize
        )
        return when (results) {
            is Results.Success ->
                LoadResult.Page(
                    data = results.data,
                    prevKey = null,
                    nextKey = if (results.data.isNullOrEmpty()) null else params.key?.next()
                )
            is Results.Failure -> {
                LoadResult.Error(
                    results.throwable
                )
            }
        }
    }

    override fun getRefreshKey(state: PagingState<OneArgPage<String>, ReceivedEvent>): OneArgPage<String>? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.next()
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }


}


