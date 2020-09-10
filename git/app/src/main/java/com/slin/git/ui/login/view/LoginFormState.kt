package com.slin.git.ui.login.view

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
        val usernameError: Int? = null,
        val passwordError: Int? = null,
        val isDataValid: Boolean = false
)
