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
import com.slin.study.kotlin.ui.floatwin.FloatWindowActivity
import com.slin.study.kotlin.ui.jetpack.JetPackActivity
import com.slin.study.kotlin.ui.librarycase.LibraryCaseActivity
import com.slin.study.kotlin.ui.material.MaterialDesignActivity
import com.slin.study.kotlin.ui.motion.MotionLayoutTestActivity
import com.slin.study.kotlin.ui.testlist.TestListFragment
import com.slin.study.kotlin.ui.testlist.TestPageData
import com.slin.study.kotlin.ui.theory.TheoryTestActivity

val testDataList =
    arrayListOf(
        TestPageData("JetPack", R.drawable.img_cartoon_1, JetPackActivity::class.java),
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
            "FloatWindow",
            R.drawable.img_cartoon_2,
            FloatWindowActivity::class.java
        ),
        TestPageData(
            "TheoryTest",
            R.drawable.img_cartoon_bear,
            TheoryTestActivity::class.java
        ),

        )
class HomeFragment : BaseFragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding




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
