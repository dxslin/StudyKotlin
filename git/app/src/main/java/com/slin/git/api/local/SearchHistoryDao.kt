package com.slin.git.api.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.slin.git.api.entity.SearchHistory


/**
 * author: slin
 * date: 2020/10/23
 * description:
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

}
