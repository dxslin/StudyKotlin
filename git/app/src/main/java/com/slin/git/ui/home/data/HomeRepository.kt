package com.slin.git.ui.home.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.slin.core.net.Results
import com.slin.core.net.processApiResponse
import com.slin.core.repository.CoreRepositoryRemote
import com.slin.core.repository.IRemoteDataSource
import com.slin.git.api.bean.PageWithArgs
import com.slin.git.api.remote.UserService
import com.slin.git.config.PAGING_REMOTE_PAGE_SIZE
import com.slin.git.entity.ReceivedEvent
import com.slin.git.manager.UserManager
import kotlinx.coroutines.flow.Flow


/**
 * author: slin
 * date: 2020/9/16
 * description: 首页仓库
 *
 */
class HomeRepository(remoteDataSource: HomeRemoteDataSource) :
    CoreRepositoryRemote<HomeRemoteDataSource>(remoteDataSource) {

    fun queryReceivedEvents(
        perPage: Int = PAGING_REMOTE_PAGE_SIZE
    ): Flow<PagingData<ReceivedEvent>> {
        val username = UserManager.INSTANCE.login
        val pageWithArgs = PageWithArgs(0, username)

        remoteDataSource.invalidate()

        return Pager(
            PagingConfig(
                pageSize = perPage,
                initialLoadSize = 30,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            pageWithArgs
        ) {
            remoteDataSource
        }.flow
    }

}

class HomeRemoteDataSource(private val userService: UserService) :
    PagingSource<PageWithArgs<String>, ReceivedEvent>(), IRemoteDataSource {

    private suspend fun queryReceivedEvents(
        username: String,
        pageIndex: Int,
        perPage: Int
    ): Results<List<ReceivedEvent>> {
        try {
            val eventsResponse = userService.queryReceivedEvents(username, pageIndex, perPage)
            return processApiResponse(eventsResponse)
        } catch (e: Exception) {
            return Results.Failure(e)
        }
    }

    override suspend fun load(params: LoadParams<PageWithArgs<String>>): LoadResult<PageWithArgs<String>, ReceivedEvent> {
        val results = queryReceivedEvents(
            params.key?.args.orEmpty(),
            params.key?.page ?: 0,
            params.loadSize
        )
        return when (results) {
            is Results.Success ->
                LoadResult.Page(
                    data = results.data,
                    prevKey = null,
                    nextKey = if (results.data.isNullOrEmpty()) null else params.key?.copy(
                        params.key?.page?.inc() ?: 0, params.key?.args
                    )
                )
            is Results.Failure -> {
                LoadResult.Error(
                    results.throwable
                )
            }
        }
    }

}


