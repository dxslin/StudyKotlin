package com.slin.git.ui.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slin.core.image.impl.ImageConfigImpl
import com.slin.git.R
import com.slin.git.databinding.ItemHomeEventsBinding
import com.slin.git.entity.ReceivedEvent
import com.slin.git.entity.Type
import com.slin.git.entity.receivedEventsDiff
import com.slin.git.utils.ImageLoaderUtils
import com.slin.git.utils.ShapeAppearanceTransformation
import com.slin.git.weight.anim.animator.*


/**
 * author: slin
 * date: 2020/9/17
 * description:
 *
 */
class ReceivedEventAdapter :
    PagingDataAdapter<ReceivedEvent, ReceiveEventViewHolder>(receivedEventsDiff),
    IItemAnimatorAdapter {

    init {
        addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.Loading) {
                animateOperator.resetAnimatePosition()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiveEventViewHolder {
        return ReceiveEventViewHolder(
            ItemHomeEventsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReceiveEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override val animateOperator: ItemAnimateOperator = ItemAnimateOperator(
        object : ItemAnimator.Factory {
            override fun create(): ItemAnimator =
                SimpleItemAnimator(object : AnimationFactory {
                    override fun create(): Animation {
                        return AnimationSet(true).apply {
                            addAnimation(
                                TranslateAnimation(
                                    TranslateAnimation.RELATIVE_TO_SELF,
                                    0.7f,
                                    TranslateAnimation.RELATIVE_TO_SELF,
                                    0f,
                                    TranslateAnimation.RELATIVE_TO_SELF,
                                    0f,
                                    TranslateAnimation.RELATIVE_TO_SELF,
                                    0f
                                ).apply {
                                    duration = 300
                                })
                            addAnimation(AlphaAnimation(0f, 1f).apply {
                                duration = 300
                            })
                        }
                    }
                })
        }).apply {
        isAnimateFirstOnly = true
    }

    override fun onViewAttachedToWindow(holder: ReceiveEventViewHolder) {
        super.onViewAttachedToWindow(holder)
        animateOperator.attachViewAnim(holder)
    }

    override fun onViewDetachedFromWindow(holder: ReceiveEventViewHolder) {
        super.onViewDetachedFromWindow(holder)
        animateOperator.detachViewAnim(holder)
    }

}

private val shapeTransformations = listOf(
    ShapeAppearanceTransformation(R.style.ShapeAppearance_SGit_SmallComponent)
)

class ReceiveEventViewHolder(private val binding: ItemHomeEventsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(receivedEvent: ReceivedEvent?) {
        binding.apply {
            event = receivedEvent
            event?.let {
                showItem(it)
            }
            executePendingBindings()
        }
    }

    private fun showItem(event: ReceivedEvent) {
        binding.apply {
            ImageLoaderUtils.loadImage {
                ImageConfigImpl(
                    ivAvatar,
                    event.actor.avatarUrl,
                    ContextCompat.getDrawable(
                        root.context,
                        R.drawable.stroked_course_image_placeholder
                    ),
                    transformations = shapeTransformations
                )
            }
            tvAction.text = when (event.type) {
                Type.WatchEvent -> "starred"
                Type.CreateEvent -> "created"
                Type.ForkEvent -> "forked"
                Type.PushEvent -> "pushed"
                else -> event.type.name
            }
        }
    }

}
