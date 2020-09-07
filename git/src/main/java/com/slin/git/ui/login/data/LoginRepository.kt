package com.slin.git.ui.login.data

import com.slin.core.net.Results
import com.slin.core.repository.IRepository
import com.slin.git.entity.UserInfo
import com.slin.git.manager.UserManager

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(
    private val remoteDataSource: LoginRemoteDataSource,
    private val localDataSource: LoginLocalDataSource
) : IRepository {

    fun logout() {
        UserManager.isLoggedIn = false
        remoteDataSource.logout()
    }

    suspend fun login(username: String, password: String): Results<UserInfo> {
        // handle login
        val result = remoteDataSource.login()

        when (result) {
            is Results.Success -> setLoggedInUser(result.data, username, password)
            is Results.Failure -> localDataSource.clearUserInfo()
        }

        return result
    }

    private fun setLoggedInUser(userInfo: UserInfo, username: String, password: String) {
        UserManager.INSTANCE = userInfo
        UserManager.isLoggedIn = true
        localDataSource.saveUser(username, password)
    }
}