package com.slin.git.ui.login.ui.login

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slin.core.di.DEFAULT_SHARE_PREFERENCES_TAG
import com.slin.git.stroage.local.GitUserInfoStorage
import com.slin.git.stroage.remote.LoginService
import com.slin.git.ui.login.data.LoginLocalDataSource
import com.slin.git.ui.login.data.LoginRemoteDataSource
import com.slin.git.ui.login.data.LoginRepository
import org.kodein.di.*
import org.kodein.di.android.x.AndroidLifecycleScope
import retrofit2.Retrofit

/**
 * author: slin
 * date: 2020-09-07
 * description:
 */
const val LOGIN_MODULE_TAG = "login_module_tag"
val loginKodeinModule = DI.Module(LOGIN_MODULE_TAG) {

    bind<GitUserInfoStorage>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        GitUserInfoStorage(instance(DEFAULT_SHARE_PREFERENCES_TAG))
    }

    bind<LoginLocalDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        LoginLocalDataSource(instance())
    }

    bind<LoginService>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        instance<Retrofit>().create(LoginService::class.java)
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