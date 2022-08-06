package com.slin.study.kotlin.ui.copydown

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dylanc.longan.toast
import com.slin.core.logger.log
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseFragment
import com.slin.study.kotlin.databinding.FragmentCopyDownloadBinding
import com.slin.study.kotlin.util.CopyState
import kotlinx.coroutines.launch

class CopyDownloadFragment : BaseFragment(R.layout.fragment_copy_download) {
    private val viewModel: CopyDownloadViewModel by viewModels()
    private val binding by lazy {
        FragmentCopyDownloadBinding.bind(requireView()).also {
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAssets.setOnClickListener { copy() }
    }

    fun copy() {
//        val processBar =
//            ProgressBar(requireContext(), null, R.style.Widget_Material3_CircularProgressIndicator)
//        processBar.minWidth = 96.dp.toInt()
//        processBar.minHeight = 96.dp.toInt()

        val processDialog = AlertDialog.Builder(requireContext())
            .setView(R.layout.dialog_process_bar)
            .setTitle("复制中")
            .create()
        lifecycleScope.launch {
            viewModel.copyState.collect {
                log { "copyState: $it" }
                when (it) {
                    is CopyState.Init -> processDialog.show()
                    is CopyState.Copying -> {
                        processDialog.setTitle("复制中(${it.index}/${it.total})")
                    }
                    is CopyState.Success -> {
                        processDialog.dismiss()
                        toast("复制成功，目录：${it.dir}")
                    }
                    is CopyState.Fail -> {
                        processDialog.dismiss()
                        showFailDialog(it)
                    }
                }
            }
        }
    }

    private fun showFailDialog(failState: CopyState.Fail) {
        AlertDialog.Builder(requireContext())
            .setTitle("复制失败")
            .setMessage("${failState.index}/${failState.total}\n${failState.e.message}")
            .setPositiveButton("确定") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
