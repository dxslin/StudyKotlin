package com.slin.git.api.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slin.git.api.entity.SearchHistory


/**
 * author: slin
 * date: 2020/10/23
 * description:
 *
 */
@Database(entities = [SearchHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao

}