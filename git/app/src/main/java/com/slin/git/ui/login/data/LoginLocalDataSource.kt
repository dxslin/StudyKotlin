package com.slin.git.ui.login.data

import com.slin.core.repository.ILocalDataSource
import com.slin.git.api.local.GitUserInfoStorage
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * author: slin
 * date: 2020-09-07
 * description:
 */
@ActivityRetainedScoped
class LoginLocalDataSource @Inject constructor(private val gitUserInfoStorage: GitUserInfoStorage) :
    ILocalDataSource {

    fun saveUser(username: String, password: String) {
        gitUserInfoStorage.username = username
        gitUserInfoStorage.password = password
    }

    fun clearUserInfo() {
        gitUserInfoStorage.username = ""
        gitUserInfoStorage.password = ""
    }

    fun isAutoLogin(): Boolean {
        return gitUserInfoStorage.isAutoLogin
    }

}