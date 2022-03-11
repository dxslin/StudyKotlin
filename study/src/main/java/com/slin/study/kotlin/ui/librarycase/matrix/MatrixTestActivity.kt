package com.slin.study.kotlin.ui.librarycase.matrix

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityMatrixTestBinding
import com.tencent.matrix.AppActiveMatrixDelegate
import com.tencent.matrix.trace.view.FrameDecorator

/**
 * MatrixTestActivity
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/2/10
 */
class MatrixTestActivity : BaseActivity() {

    companion object {
        var instance: MatrixTestActivity? = null
    }

    private lateinit var binding: ActivityMatrixTestBinding

    private lateinit var decorator: FrameDecorator

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Settings.canDrawOverlays(this)) {
                decorator.isEnable = true
                decorator.show()
            } else {
                Toast.makeText(
                    this,
                    "fail to request ACTION_MANAGE_OVERLAY_PERMISSION",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        setShowBackButton(true)
        title = "Matrix Test"

        binding = ActivityMatrixTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        decorator = FrameDecorator.getInstance(this)
        decorator.isEnable = false
        AppActiveMatrixDelegate.INSTANCE.addListener {
            if (!Settings.canDrawOverlays(this)) {
                return@addListener
            }
            if (!it) {
                decorator.dismiss()
            } else {
                decorator.show()
            }
        }

        binding.apply {
            btnTrace.setOnClickListener {
                if (decorator.isShowing) {
                    decorator.isEnable = true
                    decorator.dismiss()
                } else {
                    if (Settings.canDrawOverlays(this@MatrixTestActivity)) {
                        decorator.isEnable = true
                        decorator.show()
                    } else {
                        requestWindowPermission()
                    }
                }
            }


        }
    }

    private fun requestWindowPermission() {
        permissionLauncher.launch(
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
        )
    }

}