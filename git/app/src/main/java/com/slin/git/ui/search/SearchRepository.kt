package com.slin.git.ui.search

import com.slin.core.repository.ILocalDataSource
import com.slin.core.repository.IRepository
import com.slin.git.api.entity.SearchHistory
import com.slin.git.api.local.SearchHistoryDao


/**
 * author: slin
 * date: 2020/10/23
 * description:
 *
 */

/**
 * 保留的搜索历史数量
 */
const val RETAIN_SEARCH_HISTORY_COUNT = 10

class SearchRepository(private val localDataSource: SearchLocalDataSource) : IRepository {

    suspend fun insertSearchHistory(type: SearchHistory.SearchType, keyword: String): Long {
        val searchHistory = SearchHistory(type = type, keyword = keyword)
        val result = localDataSource.insertSearchHistory(searchHistory)
        if (result >= RETAIN_SEARCH_HISTORY_COUNT) {
            deleteByMaxKey(result - RETAIN_SEARCH_HISTORY_COUNT)
        }
        return result
    }

    suspend fun querySearchHistory(): Array<SearchHistory> {
        return localDataSource.querySearchHistory()
    }

    suspend fun deleteSearchHistory(value: SearchHistory) {
        return localDataSource.deleteSearchHistory(value)
    }

    suspend fun deleteByMaxKey(maxKey: Long) {
        return localDataSource.deleteByMaxKey(maxKey)
    }

}


class SearchLocalDataSource(private val searchHistoryDao: SearchHistoryDao) : ILocalDataSource {

    suspend fun insertSearchHistory(value: SearchHistory): Long {
        return searchHistoryDao.insert(value)
    }

    suspend fun querySearchHistory(): Array<SearchHistory> {
        return searchHistoryDao.query()
    }

    suspend fun deleteSearchHistory(value: SearchHistory) {
        return searchHistoryDao.delete(value)
    }

    suspend fun deleteByMaxKey(maxKey: Long) {
        return searchHistoryDao.deleteByMaxKey(maxKey)
    }

}

