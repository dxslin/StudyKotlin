package com.slin.git.ui.login.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slin.git.ui.login.data.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(private val loginRepository: LoginRepository) :
        ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                LoginViewModel(loginRepository) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
}