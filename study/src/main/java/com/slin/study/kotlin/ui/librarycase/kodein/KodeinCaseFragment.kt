package com.slin.study.kotlin.ui.librarycase.kodein

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseFragment


/**
 * author: slin
 * date: 2020/9/2
 * description:
 *
 */
class KodeinCaseFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kodein_case, container, false)
    }

}