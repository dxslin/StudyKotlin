package com.slin.git.ui.home.view

import android.content.Intent
import android.view.View
import com.slin.core.ui.CoreFragment
import com.slin.git.R
import com.slin.git.manager.UserManager
import com.slin.git.ui.login.LoginActivity
import org.kodein.di.DI
import org.kodein.di.instance

class HomeFragment : CoreFragment() {

    override val di: DI by DI.lazy() {
        extend(super.di)
        import(homeModule)
    }

    private val homeViewModel: HomeViewModel by instance()

    override val layoutResId: Int = R.layout.fragment_home


    override fun initView(view: View) {
        if (!UserManager.isLoggedIn) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }

    }

    override fun onResume() {
        super.onResume()
        homeViewModel.queryEvents(0)
    }

}
