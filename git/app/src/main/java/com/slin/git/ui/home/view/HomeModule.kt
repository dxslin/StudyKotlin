package com.slin.git.ui.home.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slin.git.ui.home.data.HomeRemoteDataSource
import com.slin.git.ui.home.data.HomeRepository
import org.kodein.di.*
import org.kodein.di.android.x.AndroidLifecycleScope


/**
 * author: slin
 * date: 2020/9/4
 * description: home依赖注入项
 *
 */

const val HOME_FRAGMENT_MODULE_TAG: String = "home_fragment_tag"

val homeModule = DI.Module(HOME_FRAGMENT_MODULE_TAG) {

    bind<HomeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProvider(this.context, HomeViewModelFactory(instance()))[HomeViewModel::class.java]
    }

    bind<HomeRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeRepository(instance())
    }

    bind<HomeRemoteDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeRemoteDataSource(instance())
    }

}
