package com.slin.study.kotlin.ui.material

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.util.toast
import kotlinx.android.synthetic.main.layout_material_theming_clip.*
import kotlinx.android.synthetic.main.layout_material_theming_selection.*
import kotlinx.android.synthetic.main.layout_material_theming_text_field.*

class MaterialThemingActivity : BaseActivity() {

    private val TAG: String? = MaterialThemingActivity::class.simpleName

    private val dialogItemFruits = arrayOf("苹果", "梨子", "西瓜", "桃子")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_theming)
        setShowBackButton(true)
        title = "MaterialTheming"
        chip()
        selection()
        textInputField()
    }

    /**
     * Button click listener
     */
    fun buttonClick(v: View) {
        if (v is TextView) {
            toast("Click: ${v.text}")
        }
    }

    private fun chipCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (buttonView is Chip) {
            toast("Check change: $isChecked")
        }
    }

    /**
     * 监听Chip的一些事件
     */
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

    /**
     * 监听CheckBox，RadioButton，Switch的事件
     */
    private fun selection() {
        mcb_check_box.setOnCheckedChangeListener { buttonView, isChecked ->
            toast("${if (isChecked) "Check" else "Uncheck"}: ${mcb_check_box.text}")
        }

        mrb_radio_button.setOnCheckedChangeListener { buttonView, isChecked ->
            toast("${if (isChecked) "Check" else "Uncheck"}: ${mrb_radio_button.text}")
        }

        sm_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            toast("${if (isChecked) "Check" else "Uncheck"}: ${sm_switch.text}")
        }

    }

    /**
     * TextInputLayout
     */
    private fun textInputField() {
        tiet_custom_dense_input.addTextChangedListener(onTextChanged = { text, start, before, count ->
            if ((tiet_custom_dense_input.text?.length ?: 0) > til_custom_input.counterMaxLength) {
                til_custom_input.error = "字数不能超过${til_custom_input.counterMaxLength}"
                til_custom_input.isErrorEnabled = true
            } else {
                til_custom_input.isErrorEnabled = false;
            }
        })
    }

    fun showAlertDialog(v: View) {
        MaterialAlertDialogBuilder(this)
            .setTitle("消息提示")
            .setMessage("这是消息提示")
            .setPositiveButton("确定") { _, _ ->
                Toast.makeText(this, "点击确定", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("取消") { _, _ ->
                Toast.makeText(this, "点击取消", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    fun showSimpleDialog(v: View) {
        MaterialAlertDialogBuilder(this)
            .setTitle("选择水果")
            .setItems(dialogItemFruits) { _, which ->
                Toast.makeText(this, "选择了${dialogItemFruits[which]}", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    fun showSingleChoiceDialog(v: View) {
        MaterialAlertDialogBuilder(this)
            .setTitle("选择水果")
            .setSingleChoiceItems(dialogItemFruits, -1) { _, which ->
                Toast.makeText(this, "选择了${dialogItemFruits[which]}", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("确定") { _, _ ->
                Toast.makeText(this, "点击确定", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("取消") { _, _ ->
                Toast.makeText(this, "点击取消", Toast.LENGTH_SHORT).show()
            }
            .show()

    }

    fun showMultiChoiceDialog(v: View) {
        val checkItems = BooleanArray(dialogItemFruits.size) { false }
        MaterialAlertDialogBuilder(this)
            .setTitle("选择水果")
            .setMultiChoiceItems(dialogItemFruits, checkItems) { _, which, isChecked ->
                Toast.makeText(
                    this,
                    "${if (isChecked) "选择了" else "取消选择了"}${dialogItemFruits[which]}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setPositiveButton("确定") { _, _ ->
                Toast.makeText(this, "点击确定", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("取消") { _, _ ->
                Toast.makeText(this, "点击取消", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    fun showAllButtonDialog(v: View) {
        MaterialAlertDialogBuilder(this)
            .setTitle("All Button Dialog")
            .setMessage("this is all button dialog")
            .setPositiveButton("ok") { _, _ ->
                Toast.makeText(this, "Click Ok", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Click Cancel", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("Neutral") { _, _ ->
                Toast.makeText(this, "Click Neutral", Toast.LENGTH_SHORT).show()
            }
            .setIcon(R.drawable.cartoon_2)
            .show()
    }

}