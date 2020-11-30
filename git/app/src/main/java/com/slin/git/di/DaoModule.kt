package com.slin.git.di

import android.content.Context
import androidx.room.Room
import com.slin.git.api.local.AppDatabase
import com.slin.git.api.local.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * author: slin
 * date: 2020/10/23
 * description:
 *
 */

@Module
@InstallIn(ApplicationComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "git_db")
            .build()
    }


    @Provides
    @Singleton
    fun provideSearchHistoryDao(database: AppDatabase): SearchHistoryDao {
        return database.searchHistoryDao()
    }

}