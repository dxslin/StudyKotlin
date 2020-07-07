package com.slin.study.kotlin.ui.material

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import com.google.android.material.chip.Chip
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.util.toast
import kotlinx.android.synthetic.main.layout_material_clip.*

class MaterialThemingActivity : BaseActivity() {

    private val TAG: String? = MaterialThemingActivity::class.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_theming)
        isShowBackButton = true
        chip()
    }

    private fun chip() {
        chip_entry.setOnCloseIconClickListener {
            toast("Close icon click")
        }
        chip_action.setOnClickListener(this::buttonClick)
        chip_choice.setOnCheckedChangeListener(this::chipCheckedChanged)
        cg_single_select.setOnCheckedChangeListener { group, checkedId ->
            toast(
                "single ${if (checkedId == View.NO_ID) "uncheck"
                else "check:  ${findViewById<Chip>(checkedId).text}"}"
            )
            //这里可以处理总是让选中一个
            if (group.checkedChipId == View.NO_ID) {
                chip_single_1.isChecked = true
            }
        }
        cg_single_line_select.setOnCheckedChangeListener { group, checkedId ->
            toast(
                "single line ${if (checkedId == View.NO_ID) "uncheck"
                else "check:  ${findViewById<Chip>(checkedId).text}"}"
            )

        }
    }

    fun buttonClick(v: View) {
        if (v is Button) {
            toast("Click: ${v.text}")
        }
    }

    private fun chipCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (buttonView is Chip) {
            toast("Check change: $isChecked")
        }
    }

}