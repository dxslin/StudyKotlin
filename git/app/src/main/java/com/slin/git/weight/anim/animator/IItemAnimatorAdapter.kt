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
 * Adapter继承此接口，在[RecyclerView.Adapter.onViewAttachedToWindow]方法中调用[attachViewAnim]，
 * 在[RecyclerView.Adapter.onViewDetachedFromWindow]方法中调用[detachViewAnim]
 *
 * ```kotlin
 * class ReceivedEventAdapter :
 *      PagingDataAdapter<ReceivedEvent, ReceiveEventViewHolder>(receivedEventsDiff),
 *      IItemAnimatorAdapter {
 *
 *     override fun onViewAttachedToWindow(holder: ReceiveEventViewHolder) {
 *          super.onViewAttachedToWindow(holder)
 *          attachViewAnim(holder)
 *     }

 *     override fun onViewDetachedFromWindow(holder: ReceiveEventViewHolder) {
 *          super.onViewDetachedFromWindow(holder)
 *          detachViewAnim(holder)
 *     }
 *
 * }
 * ```
 *
 *
 */
interface IItemAnimatorAdapter {

    private val itemAnimators: MutableList<ItemAnimator>
        get() = mutableListOf()

    private val listener: ItemAnimator.Listener
        get() = object : ItemAnimator.Listener {
            override fun onAnimatorStart(animator: ItemAnimator) {
                itemAnimators.add(animator)
            }

            override fun onAnimatorEnd(animator: ItemAnimator) {
                itemAnimators.remove(animator)
            }

        }

    val animatorFactory: ItemAnimator.Factory
        get() = SimpleItemAnimator.Factory()


    fun attachViewAnim(holder: RecyclerView.ViewHolder) {
        val view = holder.itemView
        val itemAnimator = animatorFactory.create()
        itemAnimator.addAnimatorListener(listener)
        itemAnimator.animate(view)
    }

    fun detachViewAnim(holder: RecyclerView.ViewHolder) {
        itemAnimators.forEach {
            it.cancel()
        }
    }

}

