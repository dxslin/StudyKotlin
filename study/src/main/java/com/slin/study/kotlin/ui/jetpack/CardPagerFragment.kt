package com.slin.study.kotlin.ui.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slin.study.kotlin.base.BaseFragment
import com.slin.study.kotlin.databinding.FragmentCardPageBinding
import com.slin.study.kotlin.ui.testlist.TestPageData

/**
 * author: slin
 * date: 2021/8/18
 * description:
 *
 */
class CardPagerFragment : BaseFragment() {

    private lateinit var pageData: TestPageData

    private lateinit var binding: FragmentCardPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pageData = arguments?.getParcelable("page")!!

        binding.apply {
            ivHead.setImageResource(pageData.icon)
            tvName.text = pageData.name
            tvContent.text = pageData.activityClass?.simpleName
        }

    }


    companion object {
        fun newInstance(page: TestPageData): CardPagerFragment {
            val args = Bundle()
            args.putParcelable("page", page)
            val fragment = CardPagerFragment()
            fragment.arguments = args
            return fragment
        }
    }


}