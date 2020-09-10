package com.slin.git.ui.login

import com.slin.core.ui.CoreFragment
import com.slin.core.ui.SingleFragmentActivity
import com.slin.git.R
import com.slin.git.ui.login.view.LoginFragment

class LoginActivity : SingleFragmentActivity() {


    override val contentFragment: CoreFragment = LoginFragment()

    override fun initView() {
        super.initView()
        title = getString(R.string.title_login)
    }

}