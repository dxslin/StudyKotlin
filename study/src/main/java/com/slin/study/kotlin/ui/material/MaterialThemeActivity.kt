package com.slin.study.kotlin.ui.material

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.util.THEME_ARRAY
import com.slin.study.kotlin.util.THEME_NIGHT_MODE
import com.slin.study.kotlin.util.ThemeHelper
import kotlinx.android.synthetic.main.layout_material_theming_theme.*


/**
 * author: slin
 * date: 2020/8/28
 * description:
 *
 */

val themeList = mutableListOf<Int>()

class MaterialThemeActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_theme)
        title = "Theme"
        setShowBackButton(true)
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
                btn_change_night_mode.text = "NightMode: ${THEME_NIGHT_MODE[which]}"
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
                btn_change_theme.text = "Theme: ${THEME_ARRAY[which]}"
                dialog.dismiss()
                if (theme != which) {
                    ThemeHelper.saveThemeIndex(which)
                    recreate()
                }
            }
            .show()
    }


}