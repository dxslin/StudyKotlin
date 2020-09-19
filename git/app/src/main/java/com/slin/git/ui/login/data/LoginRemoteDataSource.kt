package com.slin.git.ui.login.data

import com.slin.core.net.Results
import com.slin.core.net.processApiResponse
import com.slin.core.repository.IRemoteDataSource
import com.slin.git.api.bean.LoginRequestModel
import com.slin.git.api.remote.LoginService
import com.slin.git.entity.UserInfo

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginRemoteDataSource(private val loginService: LoginService) : IRemoteDataSource {

    suspend fun login(): Results<UserInfo> {
        val loginRequestModel = LoginRequestModel.generate()
        val result = processApiResponse(loginService.authorizations(loginRequestModel))
        return when (result) {
            is Results.Success -> {
                processApiResponse(loginService.fetchUserOwner())
            }
            is Results.Failure -> result
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}