package com.slin.git.api.local

import androidx.datastore.DataStore
import com.slin.core.di.SingletonHolderSingleArg
import com.slin.proto.GitUserPbOuterClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException


/**
 * author: slin
 * date: 2020/9/3
 * description: 缓存的github账户信息
 *
 */
class GitUserInfoStorage(private val ds: DataStore<GitUserPbOuterClass.GitUserPb>) {

    fun obtainGitUser(): Flow<GitUserPbOuterClass.GitUserPb> {
        return ds.data.catch { e ->
            if (e is IOException) {
                e.printStackTrace()
                emit(GitUserPbOuterClass.GitUserPb.getDefaultInstance())
            } else {
                throw e
            }
        }
    }

    suspend fun saveUserInfo(username: String, password: String) {
        ds.updateData {
            it.toBuilder()
                .setUsername(username)
                .setPassword(password)
                .build()
        }
    }

    suspend fun saveToken(token: String) {
        ds.updateData {
            it.toBuilder()
                .setToken(token)
                .build()
        }
    }

    suspend fun saveIsAutoLogin(isAutoLogin: Boolean) {
        ds.updateData {
            it.toBuilder()
                .setIsAutoLogin(isAutoLogin)
                .build()
        }
    }

    companion object :
        SingletonHolderSingleArg<GitUserInfoStorage, DataStore<GitUserPbOuterClass.GitUserPb>>(::GitUserInfoStorage)

}

