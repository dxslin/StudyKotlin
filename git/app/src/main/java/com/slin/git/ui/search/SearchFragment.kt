package com.slin.git.ui.search

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.paging.PagingData
import androidx.transition.Slide
import com.slin.git.base.BaseFragment
import com.slin.git.databinding.SearchFragmentBinding
import org.kodein.di.DI
import org.kodein.di.instance

class SearchFragment : BaseFragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    override val di: DI by DI.lazy {
        extend(super.di)
        import(searchModule)

    }

    private val viewModel: SearchViewModel by instance()

    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return SearchFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedElementEnterTransition = Slide(Gravity.END)

        binding.apply {
            etSearchInput.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    viewModel.search(etSearchInput.text.toString())
                    true
                } else {
                    false
                }
            }
            etSearchInput.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    viewModel.search(etSearchInput.text.toString())
                    true
                } else {
                    false
                }
            }

            val adapter = SearchHistoryAdapter()
            rvSearchHistory.adapter = adapter

            viewModel.historyData.observe(this@SearchFragment.viewLifecycleOwner, { data ->
                adapter.submitData(this@SearchFragment.lifecycle, PagingData.from(data))
            })

        }

        viewModel.query()
    }

}