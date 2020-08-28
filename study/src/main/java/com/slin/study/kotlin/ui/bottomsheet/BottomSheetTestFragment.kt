package com.slin.study.kotlin.ui.bottomsheet

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseFragment
import com.slin.study.kotlin.databinding.BottomSheetTestFragmentBinding
import kotlin.math.abs

class BottomSheetTestFragment : BaseFragment() {

    private val TAG = BottomSheetTestFragment::class.simpleName

    private lateinit var binding: BottomSheetTestFragmentBinding
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>
    private val imageBottomSheetDialog: BaseBottomSheetDialog by lazy {
        val dialog = BaseBottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.bottom_shett_dialog_image)
        dialog
    }

    companion object {
        fun newInstance() =
            BottomSheetTestFragment()
    }

    private lateinit var viewModel: BottomSheetTestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.bottom_sheet_test_fragment, container, false)
        binding = BottomSheetTestFragmentBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[BottomSheetTestViewModel::class.java]
        initView()
    }

    private fun initView() {
        activity?.window?.statusBarColor = Color.TRANSPARENT
//        StatusBarUtil.setTransparentForImageViewInFragment(activity, binding.coordinator)

        behavior = BottomSheetBehavior.from(binding.clBottomSheetContent)
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            var slideOffset: Float = 0f
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                this.slideOffset = slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    if (slideOffset > 0.5) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    } else {
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
            }

        })

        binding.toolbar.apply {
            setContentInsetsAbsolute(0, 0)
            setNavigationOnClickListener { activity?.onBackPressed() }
//            title = "Title"
        }

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            run {
                val percent = abs(verticalOffset * 1.0f / appBarLayout.totalScrollRange)
                Log.d(TAG, "initView: percent: $percent")
                binding.tvTitle.alpha = 1 - percent
                binding.tvActionBarTitle.alpha = percent
//                binding.toolbar.title = if (percent > 0.8) "Title" else ""
            }
        })
        binding.tvMessage.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showBottomSheetDialog() {
        context?.let {
            imageBottomSheetDialog.apply {
                if (isShowing) {
                    dismiss()
                } else {
                    show()
                }
            }
        }
    }

}