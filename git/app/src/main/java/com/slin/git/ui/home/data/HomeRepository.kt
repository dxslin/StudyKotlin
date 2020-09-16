package com.slin.git.ui.home.data

import com.slin.core.net.Results
import com.slin.core.net.processApiResponse
import com.slin.core.repository.CoreRepositoryRemote
import com.slin.core.repository.IRemoteDataSource
import com.slin.git.config.PAGING_REMOTE_PAGE_SIZE
import com.slin.git.entity.ReceivedEvent
import com.slin.git.manager.UserManager
import com.slin.git.stroage.remote.UserService


/**
 * author: slin
 * date: 2020/9/16
 * description: 首页仓库
 *
 */
class HomeRepository(remoteDataSource: HomeRemoteDataSource) :
    CoreRepositoryRemote<HomeRemoteDataSource>(remoteDataSource) {

    suspend fun queryReceivedEvents(
        pageIndex: Int,
        perPage: Int = PAGING_REMOTE_PAGE_SIZE
    ): Results<List<ReceivedEvent>> {
        val username = UserManager.INSTANCE.name
        return remoteDataSource.queryReceivedEvents(username, pageIndex, perPage)
    }

}

class HomeRemoteDataSource(private val userService: UserService) : IRemoteDataSource {

    suspend fun queryReceivedEvents(
        username: String,
        pageIndex: Int,
        perPage: Int
    ): Results<List<ReceivedEvent>> {
        val eventsResponse = userService.queryReceivedEvents(username, pageIndex, perPage)
        return processApiResponse {
            eventsResponse
        }
    }

}

