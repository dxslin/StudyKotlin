package com.slin.libkt.ui.test

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.slin.libkt.R
import com.slin.libkt.databinding.FragmentTestListBinding
import com.slin.libkt.databinding.ItemHomeTestBinding
import com.slin.libkt.view.GridDividerItemDivider
import com.slin.saber.wallpaper.base.BaseFragment


/**
 * author: slin
 * date: 2020/9/1
 * description:
 *
 */
class TestListFragment : BaseFragment<FragmentTestListBinding>(R.layout.fragment_test_list) {

    companion object {

        const val INTENT_NAME: String = "intent_name"
        const val INTENT_KEY_TEST_PAGE_DATA = "intent_key_test_page_data"

        fun newInstance(testPageData: ArrayList<TestPageData>): TestListFragment {
            val fragment = TestListFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(INTENT_KEY_TEST_PAGE_DATA, testPageData)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList: java.util.ArrayList<TestPageData>? =
            arguments?.getParcelableArrayList(INTENT_KEY_TEST_PAGE_DATA)



        binding.rvTestItem.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(GridDividerItemDivider(10, 10, Color.TRANSPARENT))
            adapter = object : BaseQuickAdapter<TestPageData, BaseViewHolder>(
                R.layout.item_home_test,
                dataList
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

