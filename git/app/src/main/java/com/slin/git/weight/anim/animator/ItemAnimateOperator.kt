package com.slin.git.weight.anim.animator

import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * author: slin
 * date: 2020/10/20
 * description:
 *
 */
class ItemAnimateOperator(private val animatorFactory: ItemAnimator.Factory = SimpleItemAnimator.DefaultSimpleFactory()) {

    private val animatorMap = mutableMapOf<View, ItemAnimator>()

    private var lastAnimatePosition = -1

    /**
     * 是否只执行一次动画，如果是那么只有第一次加载view时出现动画，
     * 当重新设置数据时需要调用[resetAnimatePosition]方法重置显示位置，否则动画不再生效
     */
    var isAnimateFirstOnly = true


    /**
     * 重置动画显示位置
     */
    fun resetAnimatePosition() {
        lastAnimatePosition = -1
    }

    fun attachViewAnim(holder: RecyclerView.ViewHolder) {
        if (!isAnimateFirstOnly || holder.layoutPosition > lastAnimatePosition) {
            val view = holder.itemView
            val itemAnimator = animatorFactory.create()
            itemAnimator.addAnimatorListener(ItemAnimatorListener(view))
            itemAnimator.animate(view)
            lastAnimatePosition = holder.layoutPosition
        }
    }

    fun detachViewAnim(holder: RecyclerView.ViewHolder) {
        animatorMap[holder.itemView]?.cancel()
    }

    inner class ItemAnimatorListener(val view: View) : ItemAnimator.Listener {
        override fun onAnimatorStart(animator: ItemAnimator) {
            animatorMap[view] = animator
        }

        override fun onAnimatorEnd(animator: ItemAnimator) {
            animatorMap.remove(view)
        }

    }
}
