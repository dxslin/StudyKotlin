package com.slin.git.api.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slin.git.api.entity.SearchHistory


/**
 * author: slin
 * date: 2020/10/23
 * description: room集成
 * 1. Import room dependencies
 * 2. Create dao interface, query/insert/delete/update @see [SearchHistoryDao]
 * 3. Create [AppDatabase] abstract class extends [RoomDatabase] with `@Database` annotation
 * 4. `Room.databaseBuilder(context, AppDatabase::class.java, "database_name").build()`
 *
 */
@Database(entities = [SearchHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao

}