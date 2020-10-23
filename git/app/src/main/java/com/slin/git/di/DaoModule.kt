package com.slin.git.di

import androidx.room.Room
import com.slin.git.api.local.AppDatabase
import com.slin.git.api.local.SearchHistoryDao
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


/**
 * author: slin
 * date: 2020/10/23
 * description:
 *
 */
const val DAO_MODULE_TAG = "dao_module_tag"

val daoModule = DI.Module(DAO_MODULE_TAG) {

    bind<AppDatabase>() with singleton {
        Room.databaseBuilder(instance(), AppDatabase::class.java, "git_db").build()
    }

    bind<SearchHistoryDao>() with singleton {
        instance<AppDatabase>().searchHistoryDao()
    }

}