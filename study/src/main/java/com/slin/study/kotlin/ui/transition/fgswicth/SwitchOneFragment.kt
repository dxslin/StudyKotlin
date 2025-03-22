package com.slin.study.kotlin.ui.transition.fgswicth

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dylanc.longan.dp
import com.slin.study.kotlin.R
import com.slin.study.kotlin.databinding.FragmentSwitchOneBinding


private const val ARG_PARAM_NAME = "arg_param_name"

class SwitchOneFragment : Fragment() {

    private val name: String? by lazy { arguments?.getString(ARG_PARAM_NAME) }
    private lateinit var binding: FragmentSwitchOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSwitchOneBinding.inflate(inflater).apply {
            binding = this
            initView()
        }.root
    }

    private fun FragmentSwitchOneBinding.initView() {
        tvTitle.text = name
        tvSwitchToAnother.setOnClickListener {
            when (name) {
                PAGE_ONE -> parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.scale_in_bigger,
                        R.anim.scale_out_smaller,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                    )
                    .replace(R.id.fl_content, newInstance(PAGE_TWO))
                    .addToBackStack(null)
                    .commit()

                PAGE_TWO -> parentFragmentManager.popBackStack()
            }
        }
        when (name) {
            PAGE_ONE -> {
                root.setBackgroundColor(Color.BLUE)
                clContent.layoutParams.apply {
                    width = 100.dp.toInt()
                    height = 400.dp.toInt()
                }
            }

            PAGE_TWO -> {
                root.setBackgroundColor(Color.MAGENTA)
                clContent.layoutParams.apply {
                    width = 200.dp.toInt()
                    height = 400.dp.toInt()
                }
            }
        }
    }

    companion object {

        const val PAGE_ONE = "one"
        const val PAGE_TWO = "two"

        fun newInstance(name: String): SwitchOneFragment {
            val args = Bundle().apply {
                putString(ARG_PARAM_NAME, name)
            }

            val fragment = SwitchOneFragment()
            fragment.arguments = args
            return fragment
        }
    }
}