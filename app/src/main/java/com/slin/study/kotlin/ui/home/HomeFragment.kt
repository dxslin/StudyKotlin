package com.slin.study.kotlin.ui.home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseFragment
import com.slin.study.kotlin.databinding.FragmentHomeBinding
import com.slin.study.kotlin.databinding.ItemHomeTestBinding
import com.slin.study.kotlin.ui.bottomsheet.BottomSheetTestActivity
import com.slin.study.kotlin.ui.material.MaterialDesignActivity
import com.slin.study.kotlin.ui.test.DataBindActivity
import com.slin.study.kotlin.ui.test.ViewBindActivity
import com.slin.study.kotlin.view.GridDividerItemDivider

const val INTENT_NAME: String = "intent_name"
const val INTENT_KEY_TEST_PAGE_DATA = "intent_key_test_page_data"

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding

    companion object {
        fun newInstance(testPageData: ArrayList<TestPageData>): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(INTENT_KEY_TEST_PAGE_DATA, testPageData)
            homeFragment.arguments = bundle
            return homeFragment
        }
    }


    private val testDataList =
        mutableListOf(
            TestPageData("ViewBind", R.mipmap.cartoon_1, ViewBindActivity::class.java),
            TestPageData("DataBind", R.mipmap.cartoon_2, DataBindActivity::class.java),
            TestPageData("BottomSheet", R.mipmap.cartoon_3, BottomSheetTestActivity::class.java),
            TestPageData("MaterialDesign", R.mipmap.cartoon_cat, MaterialDesignActivity::class.java)
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataList: java.util.ArrayList<TestPageData>? =
            arguments?.getParcelableArrayList(INTENT_KEY_TEST_PAGE_DATA)
        if (!dataList.isNullOrEmpty()) {
            testDataList.clear()
            testDataList.addAll(dataList)
        }

        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        binding = FragmentHomeBinding.inflate(inflater)

        binding.rvTestItem.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridDividerItemDivider(10, 10, Color.TRANSPARENT))
            adapter = object : BaseQuickAdapter<TestPageData, BaseViewHolder>(
                R.layout.item_home_test,
                testDataList
            ) {
                override fun convert(holder: BaseViewHolder, item: TestPageData) {
                    val testBinding = ItemHomeTestBinding.bind(holder.itemView)
                    testBinding.tvTestName.apply {
                        text = item.name
                        setCompoundDrawablesRelativeWithIntrinsicBounds(0, item.icon, 0, 0)
                        setOnClickListener { view ->
                            jumpToActivity(view, item)
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun jumpToActivity(view: View, item: TestPageData) {
        context?.let { ctx ->
            item.activityClass?.let {
                val intent = Intent(ctx, it)
                intent.putExtra(INTENT_NAME, item.name)
                startActivity(intent)
            }
        }
    }

}

data class TestPageData(val name: String, val icon: Int, var activityClass: Class<out Activity>?) :
    Parcelable {
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        null
    ) {
        val className = parcel.readString()
        try {
            @Suppress("UNCHECKED_CAST")
            activityClass = Class.forName(className) as Class<out Activity>?
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(icon)
        parcel.writeString(activityClass?.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestPageData> {
        override fun createFromParcel(parcel: Parcel): TestPageData {
            return TestPageData(parcel)
        }

        override fun newArray(size: Int): Array<TestPageData?> {
            return arrayOfNulls(size)
        }
    }
}
