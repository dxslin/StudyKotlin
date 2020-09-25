package com.slin.core.net.status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.slin.core.R


/**
 * author: slin
 * date: 2020/9/25
 * description:
 *
 */

class StateViewSwitcher(
    private val contentView: View,
    private val stateView: StateView = DefaultStateView()
) {

    /**
     * contentView 的父布局
     */
    private val parent: ViewGroup

    /**
     * contentView的参数
     */
    private val contentViewParams: ViewGroup.LayoutParams

    /**
     * contentView在parent中的位置
     */
    private val contentViewIndex: Int

    /**
     * 上次添加的状态view，为空则没有加载
     */
    private var lastAddSateView: View? = null

    /**
     * 当前状态
     */
    private var curState: State

    init {
        val contentViewParent = contentView.parent
        checkNotNull(contentViewParent) { "ContentView without parent" }
        check(contentViewParent is ViewGroup) { "ContentView parent is not a ViewGroup" }
        parent = contentViewParent
        contentViewParams = contentView.layoutParams
        contentViewIndex = parent.indexOfChild(contentView)
        curState = State.Initial
    }


    fun stateChange(state: State) {
        if (curState == state) {
            return
        }
        curState = state
        if (state is State.LoadSuccess) {
            //加载成功需要移除已添加的加载状态view
            lastAddSateView?.let {
                parent.removeView(lastAddSateView)
                lastAddSateView = null
            }
//            val index = parent.indexOfChild(contentView)
            //如果contentView没有在parent中，则将其添加进去
            if (contentView.parent == null) {
                parent.addView(contentView, contentViewIndex, contentViewParams)
            }
        } else {
            val view = stateView.getStateView(parent, state)

            //加载中或者加载失败需要将contentView移除，添加加载状态view
            if (contentView.parent != null) {
                parent.removeView(contentView)
            }

            if (lastAddSateView != null) {
                //如果创建的view与上次添加的view不一样，那么需要移除之前的，再添加
                //如果一样则不需要任何操作
                if (lastAddSateView != view) {
                    parent.removeView(lastAddSateView)
                    addStateView(view)
                }
            } else {
                // 如果上次创建的加载状态view为空，则添加新的view
                addStateView(view)
            }
        }
    }

    private fun addStateView(view: View) {
        parent.addView(view, contentViewIndex, contentViewParams)
        lastAddSateView = view
    }

}

interface StateView {
    fun getStateView(parent: ViewGroup, state: State): View
}

class DefaultStateView : StateView {

    private var loadingStateView: View? = null
    private var failStateView: View? = null
    private var failTextView: TextView? = null

    override fun getStateView(parent: ViewGroup, state: State): View {
        val context = parent.context
        return if (state == State.Initial || state == State.Loading) {
            if (loadingStateView == null) {
                createLoadingView(parent)
            }
            loadingStateView!!
        } else {
            if (failStateView == null) {
                createFailView(parent)
            }

            when (state.failState) {
                is DataFailState.NoData -> {
                    failTextView?.text = context.getString(R.string.state_view_empty_data)
                    failTextView?.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        R.drawable.ic_empty_data,
                        0,
                        0
                    )
                }
                is DataFailState.NoNetwork -> {
                    failTextView?.text = context.getString(R.string.state_view_no_network)
                    failTextView?.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        R.drawable.ic_no_network,
                        0,
                        0
                    )
                }
                is DataFailState.Error -> {
                    failTextView?.text = context.getString(R.string.state_view_error)
                    failTextView?.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        R.drawable.ic_load_fail,
                        0,
                        0
                    )
                }
            }
            failStateView!!
        }

    }

    private fun createLoadingView(parent: ViewGroup) {
        loadingStateView = LayoutInflater.from(parent.context)
            .inflate(R.layout.state_view_loading, parent, false)
    }

    private fun createFailView(parent: ViewGroup) {
        failStateView = LayoutInflater.from(parent.context)
            .inflate(R.layout.state_view_load_fail, parent, false)
        failTextView = failStateView?.findViewById(R.id.text)
    }


}
