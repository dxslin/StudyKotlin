package com.slin.git.ui.login.data

import com.slin.core.net.Results
import com.slin.core.repository.IRepository
import com.slin.git.api.entity.UserInfo
import com.slin.git.manager.UserManager
import com.slin.proto.GitUserPbOuterClass
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

@ActivityRetainedScoped
class LoginRepository @Inject constructor(
    private val remoteDataSource: LoginRemoteDataSource,
    private val localDataSource: LoginLocalDataSource,
) : IRepository {

    fun logout() {
        UserManager.userInfo.postValue(null)
        remoteDataSource.logout()
    }

    suspend fun login(username: String, password: String): Results<UserInfo> {
        //这里先保存一下用户名和密码，因为我们在拦截器里面使用了用户名和密码，如果登陆失败就清除掉
        localDataSource.saveUser(username, password)
        // handle login
        val result = remoteDataSource.login()

        when (result) {
            is Results.Success -> setLoggedInUser(result.data)
            is Results.Failure -> localDataSource.clearUserInfo()
        }

        return result
    }

    private fun setLoggedInUser(userInfo: UserInfo) {
        UserManager.userInfo.postValue(userInfo)
    }

    fun obtainGitUser(): Flow<GitUserPbOuterClass.GitUserPb> {
        return localDataSource.obtainGitUser()
    }

}