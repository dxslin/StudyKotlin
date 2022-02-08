package com.slin.study.kotlin.ui.testlist

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseFragment
import com.slin.study.kotlin.databinding.FragmentTestListBinding
import com.slin.study.kotlin.databinding.ItemHomeTestBinding
import com.slin.study.kotlin.view.GridDividerItemDivider


/**
 * author: slin
 * date: 2020/9/1
 * description:
 *
 */
class TestListFragment : BaseFragment() {

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

    private lateinit var binding: FragmentTestListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestListBinding.inflate(inflater)
        return binding.root
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

