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
import com.slin.study.kotlin.databinding.*
import com.slin.study.kotlin.ui.bottomsheet.BottomSheetTestActivity
import com.slin.study.kotlin.ui.text.TextFragment
import com.slin.study.kotlin.util.THEME_ARRAY
import com.slin.study.kotlin.util.THEME_NIGHT_MODE
import com.slin.study.kotlin.util.ThemeHelper
import com.slin.study.kotlin.util.toast
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

    private lateinit var viewBinding: ActivityMaterialThemingBinding
    private val dialogItemFruits = arrayOf("苹果", "梨子", "西瓜", "桃子")
    private var badgingEnable = false

    /**
     * 是否是重新创建
     */
    private var recreate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMaterialThemingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setShowBackButton(true)
        title = "MaterialTheming"

        recreate = savedInstanceState?.getBoolean(BUNDLE_KEY_RECREATE) ?: false
        if (recreate) {
            viewBinding.nsvContent.post {
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
                        viewBinding.nsvContent,
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

        val binding: LayoutMaterialThemingThemeBinding =
            LayoutMaterialThemingThemeBinding.bind(viewBinding.root)
        binding.btnChangeTheme.text = "Theme: ${THEME_ARRAY[ThemeHelper.getThemeIndex()]}"
        binding.btnChangeNightMode.text =
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

        val binding: LayoutMaterialThemingClipBinding =
            LayoutMaterialThemingClipBinding.bind(viewBinding.root)
        binding.chipEntry.setOnCloseIconClickListener {
            toast("Close icon click")
        }
        binding.chipAction.setOnClickListener(this::buttonClick)
        binding.chipChoice.setOnCheckedChangeListener(this::chipCheckedChanged)
        binding.cgSingleSelect.setOnCheckedChangeListener { group, checkedId ->
            toast(
                "single ${
                    if (checkedId == View.NO_ID) "uncheck"
                    else "check:  ${findViewById<Chip>(checkedId).text}"
                }"
            )
            //这里可以处理总是让选中一个
            if (group.checkedChipId == View.NO_ID) {
                binding.chipSingle1.isChecked = true
            }
        }
        binding.cgSingleLineSelect.setOnCheckedChangeListener { group, checkedId ->
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

        val binding: LayoutMaterialThemingSelectionBinding =
            LayoutMaterialThemingSelectionBinding.bind(viewBinding.root)
        binding.mcbCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            toast("${if (isChecked) "Check" else "Uncheck"}: ${binding.mcbCheckBox.text}")
        }

        binding.mrbRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            toast("${if (isChecked) "Check" else "Uncheck"}: ${binding.mrbRadioButton.text}")
        }

        binding.smSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            toast("${if (isChecked) "Check" else "Uncheck"}: ${binding.smSwitch.text}")

        }

    }

    /**
     * TextInputLayout
     */
    private fun textInputField() {

        val binding: LayoutMaterialThemingTextFieldBinding =
            LayoutMaterialThemingTextFieldBinding.bind(viewBinding.root)
        binding.tietCustomDenseInput.addTextChangedListener(onTextChanged = { text, start, before, count ->
            if ((binding.tietCustomDenseInput.text?.length
                    ?: 0) > binding.tilCustomInput.counterMaxLength
            ) {
                binding.tilCustomInput.error = "字数不能超过${binding.tilCustomInput.counterMaxLength}"
                binding.tilCustomInput.isErrorEnabled = true
            } else {
                binding.tilCustomInput.isErrorEnabled = false;
            }
        })
    }

    private fun tab() {

        val binding: LayoutMaterialThemingTabBinding =
            LayoutMaterialThemingTabBinding.bind(viewBinding.root)
        binding.viewpager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return TextFragment.newInstance("ViewPager2 $position")
            }

        }
        TabLayoutMediator(binding.tlTabLayout, binding.viewpager2) { tab, position ->
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
                if (v is TextView) {
                    v.text = "Theme: ${THEME_ARRAY[which]}"
                }
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
                if (v is TextView) {
                    v.text = "NightMode: ${THEME_NIGHT_MODE[which]}"
                }
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

        val binding: LayoutMaterialThemingBottomNavigationBinding =
            LayoutMaterialThemingBottomNavigationBinding.bind(viewBinding.root)

        when (binding.bnvBottomNavigationView.labelVisibilityMode) {
            LabelVisibilityMode.LABEL_VISIBILITY_AUTO -> {
                binding.bnvBottomNavigationView.labelVisibilityMode =
                    LabelVisibilityMode.LABEL_VISIBILITY_SELECTED
                binding.btnLabelVisibilityMode.text = "LabelVisibilityMode:Selected"
            }
            LabelVisibilityMode.LABEL_VISIBILITY_SELECTED -> {
                binding.bnvBottomNavigationView.labelVisibilityMode =
                    LabelVisibilityMode.LABEL_VISIBILITY_LABELED
                binding.btnLabelVisibilityMode.text = "LabelVisibilityMode:Labeled"
            }
            LabelVisibilityMode.LABEL_VISIBILITY_LABELED -> {
                binding.bnvBottomNavigationView.labelVisibilityMode =
                    LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
                binding.btnLabelVisibilityMode.text = "LabelVisibilityMode:UnLebeled"
            }
            LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED -> {
                binding.bnvBottomNavigationView.labelVisibilityMode =
                    LabelVisibilityMode.LABEL_VISIBILITY_AUTO
                binding.btnLabelVisibilityMode.text = "LabelVisibilityMode:AUTO"
            }

        }
    }

    fun changeHorizontalTranslationEnable(v: View) {

        val binding: LayoutMaterialThemingBottomNavigationBinding =
            LayoutMaterialThemingBottomNavigationBinding.bind(viewBinding.root)
        binding.bnvBottomNavigationView.isItemHorizontalTranslationEnabled =
            !binding.bnvBottomNavigationView.isItemHorizontalTranslationEnabled
        binding.btnHorizontalTranslationEnable.text =
            "HorizontalTranslationEnable:${binding.bnvBottomNavigationView.isItemHorizontalTranslationEnabled}"
    }


    fun changeBadgingEnable(v: View) {
        val binding: LayoutMaterialThemingBottomNavigationBinding =
            LayoutMaterialThemingBottomNavigationBinding.bind(viewBinding.root)
        binding.bnvBottomNavigationView.menu.forEachIndexed { index, item ->
            if (!badgingEnable) {
                val badgeDrawable = binding.bnvBottomNavigationView.getOrCreateBadge(item.itemId)
                badgeDrawable.number = 10f.pow(index + 1).toInt()
                if (index == binding.bnvBottomNavigationView.menu.size() - 1) {
                    badgingEnable = true
                    binding.btnBadgingEnable.text = "badgingEnable:true"
                }
            } else {
                binding.bnvBottomNavigationView.removeBadge(item.itemId)
                if (index == binding.bnvBottomNavigationView.menu.size() - 1) {
                    badgingEnable = false
                    binding.btnBadgingEnable.text = "badgingEnable:false"
                }
            }
        }
    }

    fun changeBadgingGravity(v: View) {
        val binding: LayoutMaterialThemingBottomNavigationBinding =
            LayoutMaterialThemingBottomNavigationBinding.bind(viewBinding.root)
        var btnText = ""
        val badgeGravity =
            when (binding.bnvBottomNavigationView.getBadge(R.id.navigation_home)?.badgeGravity) {
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
        binding.btnBadgingGravity.text = "BadgingGravity: $btnText"
        binding.bnvBottomNavigationView.menu.forEach { item ->
            binding.bnvBottomNavigationView.getBadge(item.itemId)?.badgeGravity = badgeGravity
        }
    }

    fun startBottomSheetActivity(v: View) {
        startActivity(Intent(this, BottomSheetTestActivity::class.java))
    }

    private fun bottomAppBar() {

        val binding: LayoutMaterialThemingBottomAppBarBinding =
            LayoutMaterialThemingBottomAppBarBinding.bind(viewBinding.root)
        binding.babBottomAppBar.replaceMenu(R.menu.bottom_nav_menu)
        binding.babBottomAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.babBottomAppBar.setOnMenuItemClickListener {
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
        binding.btnChangeFabAlignmentMode.setOnClickListener {
            val mode = when (binding.babBottomAppBar.fabAlignmentMode) {
                BottomAppBar.FAB_ALIGNMENT_MODE_CENTER -> {
                    binding.btnChangeFabAlignmentMode.text = "Fab alignment mode: END"
                    BottomAppBar.FAB_ALIGNMENT_MODE_END
                }
                BottomAppBar.FAB_ALIGNMENT_MODE_END -> {
                    binding.btnChangeFabAlignmentMode.text = "Fab alignment mode: CENTER"
                    BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                }
                else -> {
                    binding.btnChangeFabAlignmentMode.text = "Fab alignment mode: CENTER"
                    BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                }
            }
            binding.babBottomAppBar.fabAlignmentMode = mode
        }

        binding.btnChangeFabAnimationMode.setOnClickListener {
            val mode = when (binding.babBottomAppBar.fabAnimationMode) {
                BottomAppBar.FAB_ANIMATION_MODE_SCALE -> {
                    binding.btnChangeFabAnimationMode.text = "Fab animation mode: SLIDE"
                    BottomAppBar.FAB_ANIMATION_MODE_SLIDE
                }
                BottomAppBar.FAB_ANIMATION_MODE_SLIDE -> {
                    binding.btnChangeFabAnimationMode.text = "Fab animation mode: SCALE"
                    BottomAppBar.FAB_ANIMATION_MODE_SCALE
                }
                else -> {
                    binding.btnChangeFabAnimationMode.text = "Fab animation mode: SLIDE"
                    BottomAppBar.FAB_ANIMATION_MODE_SLIDE
                }
            }
            binding.babBottomAppBar.fabAnimationMode = mode
        }

        binding.btnDecreaseFabCradleMargin.setOnClickListener {
            if (binding.babBottomAppBar.fabCradleMargin > 0) {
                binding.babBottomAppBar.fabCradleMargin--
            }
            binding.btnFabCradleMarginText.text = binding.babBottomAppBar.fabCradleMargin.toString()
        }

        binding.btnIncreaseFabCradleMargin.setOnClickListener {
            binding.babBottomAppBar.fabCradleMargin++
            binding.btnFabCradleMarginText.text = binding.babBottomAppBar.fabCradleMargin.toString()
        }


        binding.btnDecreaseFabCradleCornerRadius.setOnClickListener {
            if (binding.babBottomAppBar.fabCradleRoundedCornerRadius > 0) {
                binding.babBottomAppBar.fabCradleRoundedCornerRadius--
            }
            binding.btnFabCradleCornerRadiusText.text =
                binding.babBottomAppBar.fabCradleRoundedCornerRadius.toString()
        }

        binding.btnIncreaseFabCradleCornerRadius.setOnClickListener {
            binding.babBottomAppBar.fabCradleRoundedCornerRadius++
            binding.btnFabCradleCornerRadiusText.text =
                binding.babBottomAppBar.fabCradleRoundedCornerRadius.toString()
        }


        binding.btnDecreaseCradleVerticalOffset.setOnClickListener {
            if (binding.babBottomAppBar.cradleVerticalOffset > 0) {
                binding.babBottomAppBar.cradleVerticalOffset--
                binding.btnFabCradleVerticalOffsetText.text =
                    binding.babBottomAppBar.cradleVerticalOffset.toString()
            }
        }

        binding.btnIncreaseCradleVerticalOffset.setOnClickListener {
            binding.babBottomAppBar.cradleVerticalOffset++
            binding.btnFabCradleVerticalOffsetText.text =
                binding.babBottomAppBar.cradleVerticalOffset.toString()
        }

        binding.btnChangeFabAlignmentMode.text = "Fab alignment mode: CENTER"
        binding.btnChangeFabAnimationMode.text = "Fab animation mode: SCALE"
        binding.btnFabCradleMarginText.text = binding.babBottomAppBar.fabCradleMargin.toString()
        binding.btnFabCradleCornerRadiusText.text =
            binding.babBottomAppBar.fabCradleRoundedCornerRadius.toString()
        binding.btnFabCradleVerticalOffsetText.text =
            binding.babBottomAppBar.cradleVerticalOffset.toString()
    }
}

