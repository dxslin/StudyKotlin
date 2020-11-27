package com.slin.git.ui.login.data

import com.slin.core.net.Results
import com.slin.core.repository.IRemoteDataSource
import com.slin.git.api.arguments.LoginRequestModel
import com.slin.git.api.entity.UserInfo
import com.slin.git.api.remote.LoginService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
@ActivityRetainedScoped
class LoginRemoteDataSource @Inject constructor(private val loginService: LoginService) :
    IRemoteDataSource {

    suspend fun login(): Results<UserInfo> {
        val loginRequestModel = LoginRequestModel.generate()
        return when (val result = loginService.authorizations(loginRequestModel)) {
            is Results.Success -> loginService.fetchUserOwner()
            is Results.Failure -> result
        }

    }

    fun logout() {
        // TODO: revoke authentication
    }
}