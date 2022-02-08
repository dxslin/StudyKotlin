package com.slin.study.kotlin.ui.material

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityMaterialThemeBinding
import com.slin.study.kotlin.util.THEME_ARRAY
import com.slin.study.kotlin.util.THEME_NIGHT_MODE
import com.slin.study.kotlin.util.ThemeHelper


/**
 * author: slin
 * date: 2020/8/28
 * description:
 *
 */

val themeList = mutableListOf<Int>()

class MaterialThemeActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityMaterialThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMaterialThemeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        title = "Theme"
        setShowBackButton(true)

        viewBinding.btnChangeTheme.text = "Theme: ${THEME_ARRAY[ThemeHelper.getThemeIndex()]}"
        viewBinding.btnChangeNightMode.text =
            "NightMode: ${THEME_NIGHT_MODE[ThemeHelper.getNightModeIndex()]}"
    }

    override fun applyThemeResource() {
        super.applyThemeResource()
        themeList.forEach {
            setTheme(it)
        }
    }

    fun changeThemePalette(v: View) {
        when (v.id) {
            R.id.btn_primary_palette -> {
                themeList.clear()
                themeList.add(R.style.PrimaryPalette)
                recreate()
            }
            R.id.btn_secondary_palette -> {
                themeList.clear()
                themeList.add(R.style.SecondaryPalette)
                recreate()
            }
            R.id.mix_palette -> {
                themeList.clear()
                themeList.add(R.style.PrimaryPalette)
                themeList.add(R.style.SecondaryPalette)
                recreate()
            }
        }
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
                viewBinding.btnChangeNightMode.text = "NightMode: ${THEME_NIGHT_MODE[which]}"
                dialog.dismiss()
                if (nightMode != which) {
                    ThemeHelper.saveNightModeIndex(which)
                    recreate()
                }
            }
            .show()
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
                viewBinding.btnChangeTheme.text = "Theme: ${THEME_ARRAY[which]}"
                dialog.dismiss()
                if (theme != which) {
                    ThemeHelper.saveThemeIndex(which)
                    recreate()
                }
            }
            .show()
    }


}