package com.slin.study.kotlin.ui.text

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slin.study.kotlin.R
import kotlinx.android.synthetic.main.text_fragment.*

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.text_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TextViewModel::class.java]

        tv_text.text = arguments?.getString(REQUEST_TEXT)

    }

}