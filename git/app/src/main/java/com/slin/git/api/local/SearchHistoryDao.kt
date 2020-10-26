package com.slin.git.api.local

import androidx.room.*
import com.slin.git.api.entity.SearchHistory


/**
 * author: slin
 * date: 2020/10/23
 * description: 搜索历史dao
 * support annotation, @Query/@Update/@Insert/@Delete
 * `@Query`可以使用  `:arg1`方式引用参数
 *
 */
@Dao
interface SearchHistoryDao {

    @Insert
    suspend fun insert(value: SearchHistory): Long

    @Query("SELECT * from search_history")
    suspend fun query(): Array<SearchHistory>

    @Delete
    suspend fun delete(value: SearchHistory)

    @Query("DELETE from search_history where `key` < :maxKey ")
    suspend fun deleteByMaxKey(maxKey: Long)

    @Update
    suspend fun update(value: SearchHistory)

}
