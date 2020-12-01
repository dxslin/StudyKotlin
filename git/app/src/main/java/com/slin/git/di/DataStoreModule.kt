package com.slin.git.di

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import com.slin.proto.GitUserPbOuterClass
import com.slin.proto.GitUserSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


/**
 * author: slin
 * date: 2020/12/1
 * description: data store module
 *
 */
@Module
@InstallIn(ApplicationComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideGitUserDataStore(@ApplicationContext context: Context): DataStore<GitUserPbOuterClass.GitUserPb> {
        return context.createDataStore(fileName = "git_user.pb", serializer = GitUserSerializer)
    }

}