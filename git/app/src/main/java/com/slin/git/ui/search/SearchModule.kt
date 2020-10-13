package com.slin.git.ui.search

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DI
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton


/**
 * author: slin
 * date: 2020/10/13
 * description:
 *
 */
const val SEARCH_FRAGMENT_MODULE_TAG = "search_fragment_module_tag"


val searchModule = DI.Module(SEARCH_FRAGMENT_MODULE_TAG) {

    bind<SearchViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProvider(this.context)[SearchViewModel::class.java]
    }

}