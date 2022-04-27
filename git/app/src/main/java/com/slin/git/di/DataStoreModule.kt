package com.slin.git.di

import androidx.datastore.core.DataStore
import com.slin.proto.GitUserPbOuterClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * author: slin
 * date: 2020/12/1
 * description: data store module
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideGitUserDataStore(contextCache: ContextCache): DataStore<GitUserPbOuterClass.GitUserPb> {
        return contextCache.gitUserPb
    }

}