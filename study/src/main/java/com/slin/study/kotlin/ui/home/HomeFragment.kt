package com.slin.study.kotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseFragment
import com.slin.study.kotlin.databinding.FragmentHomeBinding
import com.slin.study.kotlin.ui.bottomsheet.BottomSheetTestActivity
import com.slin.study.kotlin.ui.jetpack.DataBindActivity
import com.slin.study.kotlin.ui.jetpack.DataStoreActivity
import com.slin.study.kotlin.ui.jetpack.ViewBindActivity
import com.slin.study.kotlin.ui.jetpack.WorkManagerTestActivity
import com.slin.study.kotlin.ui.librarycase.LibraryCaseActivity
import com.slin.study.kotlin.ui.material.MaterialDesignActivity
import com.slin.study.kotlin.ui.motion.MotionLayoutTestActivity
import com.slin.study.kotlin.ui.testlist.TestListFragment
import com.slin.study.kotlin.ui.testlist.TestPageData


class HomeFragment : BaseFragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding


    private val testDataList =
        arrayListOf(
            TestPageData("ViewBind", R.drawable.img_cartoon_1, ViewBindActivity::class.java),
            TestPageData("DataBind", R.drawable.img_cartoon_2, DataBindActivity::class.java),
            TestPageData(
                "BottomSheet",
                R.drawable.img_cartoon_3,
                BottomSheetTestActivity::class.java
            ),
            TestPageData(
                "MaterialDesign",
                R.drawable.img_cartoon_cat,
                MaterialDesignActivity::class.java
            ),
            TestPageData(
                "MotionLayout",
                R.drawable.img_cartoon_pig1,
                MotionLayoutTestActivity::class.java
            ),
            TestPageData(
                "LibraryCase",
                R.drawable.img_cartoon_pig2,
                LibraryCaseActivity::class.java
            ),
            TestPageData(
                "DataStore",
                R.drawable.img_cartoon_car,
                DataStoreActivity::class.java
            ),
            TestPageData(
                "WorkManager",
                R.drawable.img_cartoon_1,
                WorkManagerTestActivity::class.java
            )

        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val testListFragment = TestListFragment.newInstance(testDataList)

        childFragmentManager.beginTransaction()
            .replace(R.id.fl_home_content, testListFragment, testListFragment.TAG)
            .addToBackStack(testListFragment.TAG)
            .commit()


        return binding.root
    }


}
