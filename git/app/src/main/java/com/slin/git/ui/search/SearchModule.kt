package com.slin.git.ui.search

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.*
import org.kodein.di.android.x.AndroidLifecycleScope


/**
 * author: slin
 * date: 2020/10/13
 * description:
 *
 */
const val SEARCH_FRAGMENT_MODULE_TAG = "search_fragment_module_tag"


val searchModule = DI.Module(SEARCH_FRAGMENT_MODULE_TAG) {

    bind<SearchViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProvider(this.context, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                    return SearchViewModel(instance()) as T
                } else {
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }

        })[SearchViewModel::class.java]
    }

    bind<SearchRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        SearchRepository(instance())
    }

    bind<SearchLocalDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        SearchLocalDataSource(instance())
    }

}