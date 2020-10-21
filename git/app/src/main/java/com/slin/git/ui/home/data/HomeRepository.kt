package com.slin.git.ui.home.data

import androidx.paging.*
import com.slin.core.net.Results
import com.slin.core.repository.CoreRepositoryNothing
import com.slin.core.repository.IRemoteDataSource
import com.slin.git.api.bean.OneArgPage
import com.slin.git.api.remote.UserService
import com.slin.git.config.PAGING_REMOTE_INIT_SIZE
import com.slin.git.config.PAGING_REMOTE_PAGE_SIZE
import com.slin.git.entity.ReceivedEvent
import com.slin.git.ext.getOrAwaitValue
import com.slin.git.manager.UserManager
import kotlinx.coroutines.flow.Flow


/**
 * author: slin
 * date: 2020/9/16
 * description: 首页仓库
 *
 */
class HomeRepository(private val userService: UserService) :
    CoreRepositoryNothing() {

    private var homeRemoteDataSource: HomeRemoteDataSource? = null

    fun queryReceivedEvents(
        perPage: Int = PAGING_REMOTE_PAGE_SIZE
    ): Flow<PagingData<ReceivedEvent>> {
        val username = UserManager.INSTANCE.login
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
            if (homeRemoteDataSource == null || pageWithArgs.page == 0) {
                HomeRemoteDataSource(userService).apply {
                    homeRemoteDataSource = this
                }
            } else {
                homeRemoteDataSource!!
            }
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
            params.key?.args.orEmpty(),
            params.key?.page ?: 0,
            params.loadSize
        )
        return when (results) {
            is Results.Success ->
                LoadResult.Page(
                    data = results.data,
                    prevKey = null,
                    nextKey = if (results.data.isNullOrEmpty()) null else params.key?.nextPage()
                )
            is Results.Failure -> {
                LoadResult.Error(
                    results.throwable
                )
            }
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<OneArgPage<String>, ReceivedEvent>): OneArgPage<String>? {
        return state.closestPageToPosition(0)?.nextKey?.resetPage()
    }

}


