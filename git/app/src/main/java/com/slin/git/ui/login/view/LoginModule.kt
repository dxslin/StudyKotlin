package com.slin.git.ui.login.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slin.git.ui.login.data.LoginLocalDataSource
import com.slin.git.ui.login.data.LoginRemoteDataSource
import com.slin.git.ui.login.data.LoginRepository
import org.kodein.di.*
import org.kodein.di.android.x.AndroidLifecycleScope

/**
 * author: slin
 * date: 2020-09-07
 * description:
 */
const val LOGIN_MODULE_TAG = "login_module_tag"
val loginModule = DI.Module(LOGIN_MODULE_TAG) {

    bind<LoginLocalDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        LoginLocalDataSource(instance())
    }

    bind<LoginRemoteDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        LoginRemoteDataSource(instance())
    }

    bind<LoginRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        LoginRepository(instance(), instance())
    }

    bind<LoginViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProvider(
                this.context,
                LoginViewModelFactory(instance())
        )[LoginViewModel::class.java]
    }

}