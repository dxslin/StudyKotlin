package com.slin.sate_view_switcher

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.slin.core.net.status.SvsState


/**
 * author: slin
 * date: 2020/9/29
 * description: 默认的状态页面，支持加载中、空数据和加载失败三个页面
 *
 */

class DefaultStateViewFactory : StateView.Factory {
    override fun create(): StateView {
        return DefaultStateView()
    }
}

open class DefaultStateView : StateView {

    private var loadingStateView: View? = null
    private var failStateView: LoadFailView? = null


    override fun getStateView(parent: ViewGroup, state: SvsState): View? {
        val context = parent.context

        return when (state) {
            is SvsState.Initial, is SvsState.Loading -> {
                getLoadingStateView(parent)
            }
            is SvsState.LoadSuccess -> {
                if (!state.hasData) {
                    getFailStateView(parent).apply {
                        failTextView.text = context.getString(R.string.svs_empty_data)
                        failTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            R.drawable.svs_ic_empty_data,
                            0,
                            0
                        )
                    }.rootView
                } else {
                    null
                }
            }
            else -> {
                getFailStateView(parent).apply {
                    failTextView.text = obtainFailText(context, state.failCause)
                    failTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        obtainFailDrawable(context, state.failCause),
                        null,
                        null
                    )
                }.rootView
            }
        }
    }

    protected open fun obtainFailText(context: Context, throwable: Throwable?): CharSequence {
        return context.getString(R.string.svs_view_error)
    }

    protected open fun obtainFailDrawable(context: Context, throwable: Throwable?): Drawable? {
        return ContextCompat.getDrawable(context, R.drawable.svs_ic_load_fail)
    }


    private fun getLoadingStateView(parent: ViewGroup): View {
        if (loadingStateView == null) {
            loadingStateView = LayoutInflater.from(parent.context)
                .inflate(R.layout.svs_default_view_loading, parent, false)
        }
        return loadingStateView!!
    }

    private fun getFailStateView(parent: ViewGroup): LoadFailView {
        if (failStateView == null) {
            failStateView = LoadFailView(parent)
        }
        return failStateView!!
    }

}

/**
 * 加载失败的界面
 */
class LoadFailView(parent: ViewGroup) {

    val rootView: View = LayoutInflater.from(parent.context)
        .inflate(R.layout.svs_default_view_load_fail, parent, false)
    val failTextView: TextView

    init {
        failTextView = rootView.findViewById(R.id.text)
    }

}

