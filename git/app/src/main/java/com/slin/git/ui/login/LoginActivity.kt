package com.slin.git.ui.login

import android.os.Bundle
import com.slin.core.ui.CoreFragment
import com.slin.git.R
import com.slin.git.base.SingleFragmentActivity
import com.slin.git.ui.login.view.LoginFragment

class LoginActivity : SingleFragmentActivity() {


    override val contentFragment: CoreFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title_login)
    }

}