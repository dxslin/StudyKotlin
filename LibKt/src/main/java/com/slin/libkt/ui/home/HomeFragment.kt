package com.slin.libkt.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.slin.libkt.R
import com.slin.libkt.databinding.FragmentHomeBinding
import com.slin.libkt.ui.svs.SvsTestActivity
import com.slin.libkt.ui.test.TestListFragment
import com.slin.libkt.ui.test.TestPageData

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    private val pageDataList = arrayListOf(
        TestPageData("StateViewSwitcher", R.drawable.img_fate_arthur2, SvsTestActivity::class.java)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeBinding.inflate(inflater, container, false)
            .apply {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fl_content, TestListFragment.newInstance(pageDataList))
                    .commit()
            }.root
    }
}