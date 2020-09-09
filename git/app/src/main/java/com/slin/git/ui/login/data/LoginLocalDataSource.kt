package com.slin.git.ui.login.data

import com.slin.core.repository.ILocalDataSource
import com.slin.git.stroage.local.GitUserInfoStorage

/**
 * author: slin
 * date: 2020-09-07
 * description:
 */
class LoginLocalDataSource(val gitUserInfoStorage: GitUserInfoStorage) : ILocalDataSource {

    fun saveUser(username: String, password: String) {
        gitUserInfoStorage.username = username
        gitUserInfoStorage.password = password
    }

    fun clearUserInfo() {
        gitUserInfoStorage.username = ""
        gitUserInfoStorage.password = ""
    }

}