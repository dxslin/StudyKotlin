package com.slin.study.kotlin.ui.bottomsheet

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * author: slin
 * date: 2020-06-16
 * description:
 */
open class BaseBottomSheetDialog : BottomSheetDialog {

    constructor(context: Context) : this(context, 0)
    constructor(context: Context, theme: Int) : super(context, theme)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.apply {
            val params = attributes
            params.dimAmount = 0f
            attributes = params
        }

    }

}
