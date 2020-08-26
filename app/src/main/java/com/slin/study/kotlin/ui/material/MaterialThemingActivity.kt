package com.slin.study.kotlin.ui.material

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.ui.bottomsheet.BottomSheetTestActivity
import com.slin.study.kotlin.ui.text.TextFragment
import com.slin.study.kotlin.util.THEME_ARRAY
import com.slin.study.kotlin.util.THEME_NIGHT_MODE
import com.slin.study.kotlin.util.ThemeHelper
import com.slin.study.kotlin.util.toast
import kotlinx.android.synthetic.main.activity_material_theming.*
import kotlinx.android.synthetic.main.layout_material_theming_bottom_app_bar.*
import kotlinx.android.synthetic.main.layout_material_theming_bottom_navigation.*
import kotlinx.android.synthetic.main.layout_material_theming_clip.*
import kotlinx.android.synthetic.main.layout_material_theming_selection.*
import kotlinx.android.synthetic.main.layout_material_theming_tab.*
import kotlinx.android.synthetic.main.layout_material_theming_text_field.*
import kotlinx.android.synthetic.main.layout_material_theming_theme.*
import kotlin.math.hypot
import kotlin.math.pow

/**
 * author: slin
 * date: 2020/8/5
 * description:
 * 1. 一些常用的控件测试代码
 * 2. 主题切换测试代码
 */

const val BUNDLE_KEY_RECREATE = "bundle_key_recreate"

class MaterialThemingActivity : BaseActivity() {

    private val dialogItemFruits = arrayOf("苹果", "梨子", "西瓜", "桃子")
    private var badgingEnable = false

    /**
     * 是否是重新创建
     */
    private var recreate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_material_theming)
        setShowBackButton(true)
        title = "MaterialTheming"

        recreate = savedInstanceState?.getBoolean(BUNDLE_KEY_RECREATE) ?: false
        if (recreate) {
            nsv_content.post {
//                val cx = btn_change_theme.width / 2 + btn_change_theme.left
//                val cy = btn_change_theme.height / 2 + btn_change_theme.top
                val cx = resources.displayMetrics.widthPixels / 2
                val cy = resources.displayMetrics.heightPixels / 2
                val radius = hypot(
                    resources.displayMetrics.widthPixels.toDouble(),
                    resources.displayMetrics.heightPixels.toDouble()
                )
                val anim =
                    ViewAnimationUtils.createCircularReveal(
                        nsv_content,
                        cx,
                        cy,
                        0f,
                        radius.toFloat()
                    )
                anim.duration = 1000
                anim.start()
            }
            recreate = false
        }

        btn_change_theme.text = "Theme: ${THEME_ARRAY[ThemeHelper.getThemeIndex()]}"
        btn_change_night_mode.text =
            "NightMode: ${THEME_NIGHT_MODE[ThemeHelper.getNightModeIndex()]}"
        chip()
        selection()
        textInputField()
        tab()
        bottomAppBar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BUNDLE_KEY_RECREATE, recreate)
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
                "single ${
                    if (checkedId == View.NO_ID) "uncheck"
                    else "check:  ${findViewById<Chip>(checkedId).text}"
                }"
            )
            //这里可以处理总是让选中一个
            if (group.checkedChipId == View.NO_ID) {
                chip_single_1.isChecked = true
            }
        }
        cg_single_line_select.setOnCheckedChangeListener { group, checkedId ->
            toast(
                "single line ${
                    if (checkedId == View.NO_ID) "uncheck"
                    else "check:  ${findViewById<Chip>(checkedId).text}"
                }"
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

    private fun tab() {
        viewpager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return TextFragment.newInstance("ViewPager2 $position")
            }

        }
        TabLayoutMediator(tl_tab_layout, viewpager2) { tab, position ->
            tab.text = "tab text $position"
        }.attach()
    }

    private fun recreateActivity() {
        recreate = true
        ThemeHelper.recreate()
    }

    /**
     * 切换主题
     */
    fun changeTheme(v: View) {
        val theme = ThemeHelper.getThemeIndex()
        MaterialAlertDialogBuilder(this)
            .setTitle("选择主题")
            .setSingleChoiceItems(THEME_ARRAY, theme) { dialog, which ->
                Toast.makeText(this, "选择了${THEME_ARRAY[which]}", Toast.LENGTH_SHORT).show()
                btn_change_theme.text = "Theme: ${THEME_ARRAY[which]}"
                dialog.dismiss()
                if (theme != which) {
                    ThemeHelper.saveThemeIndex(which)
                    recreateActivity()
                }
            }
            .show()
    }

    /**
     * 切换夜间模式
     */
    fun changeNightMode(v: View) {
        val nightMode = ThemeHelper.getNightModeIndex()
        MaterialAlertDialogBuilder(this)
            .setTitle("夜间模式")
            .setSingleChoiceItems(THEME_NIGHT_MODE, nightMode) { dialog, which ->
                Toast.makeText(this, "选择了${THEME_NIGHT_MODE[which]}", Toast.LENGTH_SHORT).show()
                btn_change_night_mode.text = "NightMode: ${THEME_NIGHT_MODE[which]}"
                dialog.dismiss()
                if (nightMode != which) {
                    ThemeHelper.saveNightModeIndex(which)
                    recreateActivity()
                }
            }
            .show()
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
        val theme = ThemeHelper.getThemeIndex()
        MaterialAlertDialogBuilder(this)
            .setTitle("选择主题")
            .setSingleChoiceItems(dialogItemFruits, theme) { dialog, which ->
                Toast.makeText(this, "选择了${THEME_ARRAY[which]}", Toast.LENGTH_SHORT).show()
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
            .setIcon(R.drawable.img_cartoon_2)
            .show()
    }

    fun changeLabelVisibilityMode(v: View) {
        when (bnv_bottom_navigation_view.labelVisibilityMode) {
            LabelVisibilityMode.LABEL_VISIBILITY_AUTO -> {
                bnv_bottom_navigation_view.labelVisibilityMode =
                    LabelVisibilityMode.LABEL_VISIBILITY_SELECTED
                btn_label_visibility_mode.text = "LabelVisibilityMode:Selected"
            }
            LabelVisibilityMode.LABEL_VISIBILITY_SELECTED -> {
                bnv_bottom_navigation_view.labelVisibilityMode =
                    LabelVisibilityMode.LABEL_VISIBILITY_LABELED
                btn_label_visibility_mode.text = "LabelVisibilityMode:Labeled"
            }
            LabelVisibilityMode.LABEL_VISIBILITY_LABELED -> {
                bnv_bottom_navigation_view.labelVisibilityMode =
                    LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
                btn_label_visibility_mode.text = "LabelVisibilityMode:UnLebeled"
            }
            LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED -> {
                bnv_bottom_navigation_view.labelVisibilityMode =
                    LabelVisibilityMode.LABEL_VISIBILITY_AUTO
                btn_label_visibility_mode.text = "LabelVisibilityMode:AUTO"
            }

        }
    }

    fun changeHorizontalTranslationEnable(v: View) {
        bnv_bottom_navigation_view.isItemHorizontalTranslationEnabled =
            !bnv_bottom_navigation_view.isItemHorizontalTranslationEnabled
        btn_horizontal_translation_enable.text =
            "HorizontalTranslationEnable:${bnv_bottom_navigation_view.isItemHorizontalTranslationEnabled}"
    }


    fun changeBadgingEnable(v: View) {
        bnv_bottom_navigation_view.menu.forEachIndexed { index, item ->
            if (!badgingEnable) {
                val badgeDrawable = bnv_bottom_navigation_view.getOrCreateBadge(item.itemId)
                badgeDrawable.number = 10f.pow(index + 1).toInt()
                if (index == bnv_bottom_navigation_view.menu.size() - 1) {
                    badgingEnable = true
                    btn_badging_enable.text = "badgingEnable:true"
                }
            } else {
                bnv_bottom_navigation_view.removeBadge(item.itemId)
                if (index == bnv_bottom_navigation_view.menu.size() - 1) {
                    badgingEnable = false
                    btn_badging_enable.text = "badgingEnable:false"
                }
            }
        }
    }

    fun changeBadgingGravity(v: View) {
        var btnText = ""
        val badgeGravity =
            when (bnv_bottom_navigation_view.getBadge(R.id.navigation_home)?.badgeGravity) {
                BadgeDrawable.TOP_END -> {
                    btnText = "TOP_START"
                    BadgeDrawable.TOP_START
                }
                BadgeDrawable.TOP_START -> {
                    btnText = "TOP_START"
                    BadgeDrawable.BOTTOM_START
                }
                BadgeDrawable.BOTTOM_START -> {
                    btnText = "BOTTOM_END"
                    BadgeDrawable.BOTTOM_END
                }
                BadgeDrawable.BOTTOM_END -> {
                    btnText = "TOP_END"
                    BadgeDrawable.TOP_END
                }
                else -> {
                    btnText = "TOP_END"
                    BadgeDrawable.TOP_END
                }
            }
        btn_badging_gravity.text = "BadgingGravity: $btnText"
        bnv_bottom_navigation_view.menu.forEach { item ->
            bnv_bottom_navigation_view.getBadge(item.itemId)?.badgeGravity = badgeGravity
        }
    }

    fun startBottomSheetActivity(v: View) {
        startActivity(Intent(this, BottomSheetTestActivity::class.java))
    }

    private fun bottomAppBar() {
        bab_bottom_app_bar.replaceMenu(R.menu.bottom_nav_menu)
        bab_bottom_app_bar.setNavigationOnClickListener {
            onBackPressed()
        }
        bab_bottom_app_bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    Toast.makeText(this, "menu home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.navigation_dashboard -> {
                    Toast.makeText(this, "menu dashboard", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.navigation_notifications -> {
                    Toast.makeText(this, "menu notifications", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        btn_change_fab_alignment_mode.setOnClickListener {
            val mode = when (bab_bottom_app_bar.fabAlignmentMode) {
                BottomAppBar.FAB_ALIGNMENT_MODE_CENTER -> {
                    btn_change_fab_alignment_mode.text = "Fab alignment mode: END"
                    BottomAppBar.FAB_ALIGNMENT_MODE_END
                }
                BottomAppBar.FAB_ALIGNMENT_MODE_END -> {
                    btn_change_fab_alignment_mode.text = "Fab alignment mode: CENTER"
                    BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                }
                else -> {
                    btn_change_fab_alignment_mode.text = "Fab alignment mode: CENTER"
                    BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                }
            }
            bab_bottom_app_bar.fabAlignmentMode = mode
        }

        btn_change_fab_animation_mode.setOnClickListener {
            val mode = when (bab_bottom_app_bar.fabAnimationMode) {
                BottomAppBar.FAB_ANIMATION_MODE_SCALE -> {
                    btn_change_fab_animation_mode.text = "Fab animation mode: SLIDE"
                    BottomAppBar.FAB_ANIMATION_MODE_SLIDE
                }
                BottomAppBar.FAB_ANIMATION_MODE_SLIDE -> {
                    btn_change_fab_animation_mode.text = "Fab animation mode: SCALE"
                    BottomAppBar.FAB_ANIMATION_MODE_SCALE
                }
                else -> {
                    btn_change_fab_animation_mode.text = "Fab animation mode: SLIDE"
                    BottomAppBar.FAB_ANIMATION_MODE_SLIDE
                }
            }
            bab_bottom_app_bar.fabAnimationMode = mode
        }

        btn_decrease_fab_cradle_margin.setOnClickListener {
            if (bab_bottom_app_bar.fabCradleMargin > 0) {
                bab_bottom_app_bar.fabCradleMargin--
            }
            btn_fab_cradle_margin_text.text = bab_bottom_app_bar.fabCradleMargin.toString()
        }

        btn_increase_fab_cradle_margin.setOnClickListener {
            bab_bottom_app_bar.fabCradleMargin++
            btn_fab_cradle_margin_text.text = bab_bottom_app_bar.fabCradleMargin.toString()
        }


        btn_decrease_fab_cradle_corner_radius.setOnClickListener {
            if (bab_bottom_app_bar.fabCradleRoundedCornerRadius > 0) {
                bab_bottom_app_bar.fabCradleRoundedCornerRadius--
            }
            btn_fab_cradle_corner_radius_text.text =
                bab_bottom_app_bar.fabCradleRoundedCornerRadius.toString()
        }

        btn_increase_fab_cradle_corner_radius.setOnClickListener {
            bab_bottom_app_bar.fabCradleRoundedCornerRadius++
            btn_fab_cradle_corner_radius_text.text =
                bab_bottom_app_bar.fabCradleRoundedCornerRadius.toString()
        }


        btn_decrease_cradle_vertical_offset.setOnClickListener {
            if (bab_bottom_app_bar.cradleVerticalOffset > 0) {
                bab_bottom_app_bar.cradleVerticalOffset--
                btn_fab_cradle_vertical_offset_text.text =
                    bab_bottom_app_bar.cradleVerticalOffset.toString()
            }
        }

        btn_increase_cradle_vertical_offset.setOnClickListener {
            bab_bottom_app_bar.cradleVerticalOffset++
            btn_fab_cradle_vertical_offset_text.text =
                bab_bottom_app_bar.cradleVerticalOffset.toString()
        }

        btn_change_fab_alignment_mode.text = "Fab alignment mode: CENTER"
        btn_change_fab_animation_mode.text = "Fab animation mode: SCALE"
        btn_fab_cradle_margin_text.text = bab_bottom_app_bar.fabCradleMargin.toString()
        btn_fab_cradle_corner_radius_text.text =
            bab_bottom_app_bar.fabCradleRoundedCornerRadius.toString()
        btn_fab_cradle_vertical_offset_text.text =
            bab_bottom_app_bar.cradleVerticalOffset.toString()
    }
}

