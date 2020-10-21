package com.slin.git.weight.anim.animator

import androidx.recyclerview.widget.RecyclerView


/**
 * author: slin
 * date: 2020/10/19
 * description: adapter实现Item动画的接口
 *
 * 因为Adapter使用paging需要继承PagingDataAdapter，因此将item动画实现移动到此处实现
 *
 * 使用：
 *
 * Adapter继承此接口，并重写[animateOperator](如果需要自定义动画的话)，
 * 在[RecyclerView.Adapter.onViewAttachedToWindow]方法中调用[ItemAnimateOperator.attachViewAnim]，
 * 在[RecyclerView.Adapter.onViewDetachedFromWindow]方法中调用[ItemAnimateOperator.detachViewAnim]
 *
 * ```kotlin
 * class ReceivedEventAdapter :
 *      PagingDataAdapter<ReceivedEvent, ReceiveEventViewHolder>(receivedEventsDiff),
 *      IItemAnimatorAdapter {
 *
 *     override fun onViewAttachedToWindow(holder: ReceiveEventViewHolder) {
 *          super.onViewAttachedToWindow(holder)
 *          animateOperator.attachViewAnim(holder)
 *     }
 *
 *     override fun onViewDetachedFromWindow(holder: ReceiveEventViewHolder) {
 *          super.onViewDetachedFromWindow(holder)
 *          animateOperator.detachViewAnim(holder)
 *     }
 *
 * }
 * ```
 *
 *
 */
interface IItemAnimatorAdapter {

    val animateOperator: ItemAnimateOperator
        get() = ItemAnimateOperator()


}

