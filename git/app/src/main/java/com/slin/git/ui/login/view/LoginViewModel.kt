package com.slin.git.ui.login.view

import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slin.core.net.Results
import com.slin.git.R
import com.slin.git.ui.login.data.LoginRepository
import com.slin.proto.GitUserPbOuterClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

/**
 * 登录 viewmodel
 */
class LoginViewModel @ViewModelInject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun autoLogin(username: String, password: String) {
        viewModelScope.launch {
            loginRepository.obtainGitUser()
                .filter { it.isAutoLogin }
                .collect {
                    login(username, password)
                }
        }
    }

    fun login(username: String, password: String) {

        viewModelScope.launch {
            val result = loginRepository.login(username, password)

            if (result is Results.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = result.data.name))
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }

    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun obtainGitUser(): Flow<GitUserPbOuterClass.GitUserPb> {
        return loginRepository.obtainGitUser()
    }


}