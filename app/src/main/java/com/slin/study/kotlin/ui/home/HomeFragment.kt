package com.slin.study.kotlin.ui.home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.slin.study.kotlin.R
import com.slin.study.kotlin.databinding.FragmentHomeBinding
import com.slin.study.kotlin.databinding.ItemHomeTestBinding
import com.slin.study.kotlin.ui.bottomsheet.BottomSheetTestActivity
import com.slin.study.kotlin.ui.test.DataBindActivity
import com.slin.study.kotlin.ui.test.ViewBindActivity
import com.slin.study.kotlin.view.GridDividerItemDivider

const val INTENT_NAME: String = "intent_name"

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding

    private val testDataList =
        mutableListOf(
            HomeTestData("ViewBind", R.mipmap.cartoon_1, ViewBindActivity::class.java),
            HomeTestData("DataBind", R.mipmap.cartoon_2, DataBindActivity::class.java),
            HomeTestData("BottomSheet", R.mipmap.cartoon_3, BottomSheetTestActivity::class.java)
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater)

        binding.rvTestItem.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridDividerItemDivider(10, 10, Color.TRANSPARENT))
            adapter = object : BaseQuickAdapter<HomeTestData, BaseViewHolder>(
                R.layout.item_home_test,
                testDataList
            ) {
                override fun convert(holder: BaseViewHolder, item: HomeTestData) {
                    val testBinding = ItemHomeTestBinding.bind(holder.itemView)
                    testBinding.tvTestName.apply {
                        text = item.name
                        setCompoundDrawablesRelativeWithIntrinsicBounds(0, item.icon, 0, 0)
                        setOnClickListener {
                            jumpToActivity(item)
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun jumpToActivity(item: HomeTestData) {
        context?.let { ctx ->
            item.activityClass?.let {
                val intent = Intent(ctx, it)
                intent.putExtra(INTENT_NAME, item.name)
                startActivity(intent)
            }
        }
    }

}

data class HomeTestData(val name: String, val icon: Int, val activityClass: Class<out Activity>?)
