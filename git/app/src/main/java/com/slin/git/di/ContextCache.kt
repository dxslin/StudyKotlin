package com.slin.git.di

import android.content.Context
import androidx.datastore.dataStore
import com.slin.proto.GitUserSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.properties.ReadOnlyProperty

/**
 * 保存某些跟Context代理相关的东西，比如dataStore只能通过context代理创建
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/4/27
 */
@Module
@InstallIn(SingletonComponent::class)
class ContextCacheModule {

    @Provides
    @Singleton
    fun provideContextCache(@ApplicationContext context: Context): ContextCache {
        return ContextCache(context)
    }

}

class ContextCache(@ApplicationContext val context: Context) {

    val gitUserPb by dataStore(fileName = "git_user.pb", serializer = GitUserSerializer).thisScope()

    /**
     * 将 ReadOnlyProperty<Context, T> 转为 ReadOnlyProperty<Any, T>
     */
    private fun <T> ReadOnlyProperty<Context, T>.thisScope(): ReadOnlyProperty<Any, T> {
        return ReadOnlyProperty { _, property ->
            getValue(context, property)
        }
    }

}