package com.slin.git.ui.search

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.Slide
import com.slin.git.base.BaseFragment
import com.slin.git.databinding.SearchFragmentBinding
import org.kodein.di.DI
import org.kodein.di.instance

class SearchFragment : BaseFragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    override val di: DI by DI.lazy {
        extend(super.di)

    }

    private val viewModel: SearchViewModel by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return SearchFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedElementEnterTransition = Slide(Gravity.END)


    }

}