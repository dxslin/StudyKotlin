package com.slin.libkt.ui.svs

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.slin.core.net.status.SvsState
import com.slin.libkt.R
import com.slin.libkt.databinding.FragmentSvsTestBinding
import com.slin.sate_view_switcher.DefaultStateView
import com.slin.sate_view_switcher.StateView
import com.slin.sate_view_switcher.StateViewSwitcher
import java.io.IOException

class SvsTestFragment : Fragment() {

    init {
        StateViewSwitcher.config(object : StateView.Factory {
            override fun create(): StateView {
                return TestStateView()
            }
        })
    }

    private lateinit var stateViewSwitcher: StateViewSwitcher

    private lateinit var binding: FragmentSvsTestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSvsTestBinding.inflate(inflater, container, false).apply {
            binding = this
            initView()
        }.root
    }

    private fun FragmentSvsTestBinding.initView() {

        stateViewSwitcher = StateViewSwitcher(recyclerView)

        btnInitial.setOnClickListener { stateViewSwitcher.stateChange(SvsState.Initial) }
        btnLoading.setOnClickListener { stateViewSwitcher.stateChange(SvsState.Loading(false)) }
        btnLoadSuccess.setOnClickListener {
            stateViewSwitcher.stateChange(
                SvsState.LoadSuccess(
                    true
                )
            )
        }
        btnLoadFailNoData.setOnClickListener {
            stateViewSwitcher.stateChange(
                SvsState.LoadSuccess(
                    false
                )
            )
        }
        btnLoadFailNoNetwork.setOnClickListener {
            stateViewSwitcher.stateChange(
                SvsState.LoadFail(
                    NoNetworkException()
                )
            )
        }
        btnLoadFailError.setOnClickListener {
            stateViewSwitcher.stateChange(
                SvsState.LoadFail(
                    IOException("IOError")
                )
            )
        }


    }


    companion object {
        @JvmStatic
        fun newInstance() = SvsTestFragment()
    }
}


class NoNetworkException : Exception()

class TestStateView : DefaultStateView() {

    private fun isNoNetwork(throwable: Throwable?): Boolean {
        return throwable is NoNetworkException
    }

    override fun obtainFailDrawable(context: Context, throwable: Throwable?): Drawable? {
        return if (isNoNetwork(throwable)) {
            ContextCompat.getDrawable(context, R.drawable.svs_ic_no_network)
        } else {
            ContextCompat.getDrawable(context, R.drawable.svs_ic_load_fail)
        }
    }

    override fun obtainFailText(context: Context, throwable: Throwable?): CharSequence {
        return if (isNoNetwork(throwable)) {
            context.getString(R.string.lk_svs_no_network)
        } else {
            context.getString(R.string.lk_svs_error)
        }
    }

}
