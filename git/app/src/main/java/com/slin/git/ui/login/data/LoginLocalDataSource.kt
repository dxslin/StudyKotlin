package com.slin.git.ui.login.data

import com.slin.core.repository.ILocalDataSource
import com.slin.git.api.local.GitUserInfoStorage
import com.slin.proto.GitUserPbOuterClass
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * author: slin
 * date: 2020-09-07
 * description:
 */
@ActivityRetainedScoped
class LoginLocalDataSource @Inject constructor(private val gitUserInfoStorage: GitUserInfoStorage) :
    ILocalDataSource {

    suspend fun saveUser(username: String, password: String) {
        gitUserInfoStorage.saveUserInfo(username, password)
    }

    suspend fun clearUserInfo() {
        gitUserInfoStorage.saveUserInfo("", "")
    }

    fun obtainGitUser(): Flow<GitUserPbOuterClass.GitUserPb> {
        return gitUserInfoStorage.obtainGitUser()
    }

}