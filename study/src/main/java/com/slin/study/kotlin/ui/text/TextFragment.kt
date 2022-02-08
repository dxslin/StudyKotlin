package com.slin.study.kotlin.ui.text

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slin.study.kotlin.databinding.TextFragmentBinding

const val REQUEST_TEXT = "request_text"

class TextFragment : Fragment() {


    companion object {
        fun newInstance(text: String): TextFragment {
            val fragment = TextFragment()
            val bundle = Bundle()
            bundle.putString(REQUEST_TEXT, text)
            fragment.arguments = bundle
            return fragment;
        }
    }

    private lateinit var viewModel: TextViewModel
    private lateinit var binding: TextFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TextFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TextViewModel::class.java]

        binding.tvText.text = arguments?.getString(REQUEST_TEXT)

    }

}