package com.slin.study.kotlin.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * author: slin
 * date: 2021/8/11
 * description: 卡片LayoutManager
 */
public class CardLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {

    private final static String TAG = CardLayoutManager.class.getSimpleName();

    /**
     * 一次完整的聚焦滑动所需要的移动距离
     */
    private float onceCompleteScrollLength = -1;

    /**
     * 第一个子view的偏移量
     */
    private float firstChildCompleteScrollLength = -1;

    /**
     * 屏幕可见第一个view的position
     */
    private int mFirstVisiPos;

    /**
     * 屏幕可见的最后一个view的position
     */
    private int mLastVisiPos;

    /**
     * 水平方向累计偏移量
     */
    private long mHorizontalOffset;

    private int childWidth = 0;

    /**
     * 是否自动选中
     */
    private boolean isAutoSelect = true;
    private ValueAnimator selectAnimator;

    /**
     * View之间距离偏移量
     */
    private int normalViewTrans = 10;
    /**
     * View之间背景透明度比例
     */
    private float normalViewAlpha = 0.8f;
    /**
     * View之间背景缩放比例
     */
    private float normalViewScale = 0.9f;

    /**
     * 提前绘制的View数量
     */
    private int preLayoutViewCount = 2;

    private OnCardSelectedListener onCardSelectedListener;

    private int perMoveOffsetX = 0;


    public CardLayoutManager() {
        super();
    }

    public static float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //  super.onLayoutChildren(recycler, state);
        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }

        onceCompleteScrollLength = -1;

        // 分离全部已有的view 放入临时缓存  mAttachedScrap 集合中
        detachAndScrapAttachedViews(recycler);

        fill(recycler, state, 0);
    }

    private int fill(RecyclerView.Recycler recycler, RecyclerView.State state, int dx) {
        int resultDelta = dx;
        resultDelta = fillHorizontalLeft(recycler, state, dx);
        recycleChildren(recycler);
        return resultDelta;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        Log.d(TAG, "scrollHorizontallyBy: dx = " + dx);
        // 手指从右向左滑动，dx > 0; 手指从左向右滑动，dx < 0;
        if (dx == 0 || getChildCount() == 0) {
            return 0;
        }

        float realDx = dx / 1.0f;
        if (Math.abs(realDx) < 0.00000001f) {
            return 0;
        }

        perMoveOffsetX += dx;
        mHorizontalOffset += dx;

        dx = fill(recycler, state, dx);

        return dx;
    }

    /**
     * 最大偏移量
     *
     * @return
     */
    private float getMaxOffset() {
        if (childWidth == 0 || getItemCount() == 0) return 0;
        return childWidth * (getItemCount() - 1);
    }

    /**
     * 获取最小的偏移量
     *
     * @return
     */
    private float getMinOffset() {
        if (childWidth == 0) return 0;
        return (getWidth() - childWidth) / 2;
    }

    private int fillHorizontalLeft(RecyclerView.Recycler recycler, RecyclerView.State state, int dx) {
        //----------------1、边界检测-----------------
        if (dx < 0) {
            // 已到达左边界
            if (mHorizontalOffset < 0) {
                mHorizontalOffset = dx = 0;
            }
        }

        if (dx > 0) {
            if (mHorizontalOffset >= getMaxOffset()) {
                // 因为在因为scrollHorizontallyBy里加了一次dx，现在减回去
                // mHorizontalOffset -= dx;
                mHorizontalOffset = (long) getMaxOffset();
                dx = 0;
            }
        }

        // 分离全部的view，加入到临时缓存
        detachAndScrapAttachedViews(recycler);

        float startX = 0;
        float fraction = 0f;
        boolean isChildLayoutLeft = true;
        //用做层叠用掉的高度
        int heightUsed = (preLayoutViewCount + 1) * normalViewTrans;

        View tempView = null;
        int tempPosition = -1;

        if (onceCompleteScrollLength == -1) {
            // 因为mFirstVisiPos在下面可能被改变，所以用tempPosition暂存一下
            tempPosition = mFirstVisiPos;
            tempView = recycler.getViewForPosition(tempPosition);
            measureChildWithMargins(tempView, 0, heightUsed);
            childWidth = getDecoratedMeasurementHorizontal(tempView);
        }

        // 修正第一个可见view mFirstVisiPos 已经滑动了多少个完整的onceCompleteScrollLength就代表滑动了多少个item
        firstChildCompleteScrollLength = getWidth() / 2 + childWidth / 2;
        if (mHorizontalOffset >= firstChildCompleteScrollLength) {
            startX = 0;
            onceCompleteScrollLength = childWidth;
            mFirstVisiPos = (int) Math.floor(Math.abs(mHorizontalOffset - firstChildCompleteScrollLength) / onceCompleteScrollLength) + 1;
            fraction = (Math.abs(mHorizontalOffset - firstChildCompleteScrollLength) % onceCompleteScrollLength) / (onceCompleteScrollLength * 1.0f);
        } else {
            mFirstVisiPos = 0;
            startX = getMinOffset();
            onceCompleteScrollLength = firstChildCompleteScrollLength;
            fraction = (Math.abs(mHorizontalOffset) % onceCompleteScrollLength) / (onceCompleteScrollLength * 1.0f);
        }

        // 临时将mLastVisiPos赋值为getItemCount() - 1，放心，下面遍历时会判断view是否已溢出屏幕，并及时修正该值并结束布局
//        mLastVisiPos = getItemCount() - 1;

        float normalViewOffset = onceCompleteScrollLength * fraction;
        boolean isNormalViewOffsetSetted = false;

        mLastVisiPos = Math.min(mFirstVisiPos + preLayoutViewCount, getItemCount() - 1);

        //----------------3、开始布局-----------------
        for (int i = mFirstVisiPos; i <= mLastVisiPos; i++) {
            View item;
            if (i == tempPosition && tempView != null) {
                // 如果初始化数据时已经取了一个临时view
                item = tempView;
            } else {
                item = recycler.getViewForPosition(i);
            }

            int focusPosition = (int) (Math.abs(mHorizontalOffset) / childWidth);
            if (i <= focusPosition) {
                addView(item);
            } else {
                addView(item, 0);
            }
            measureChildWithMargins(item, 0, heightUsed);

            int l, t, r, b;
            l = (int) (mFirstVisiPos == i ? startX - normalViewOffset : startX);
            t = getPaddingTop() + heightUsed;
            r = l + getDecoratedMeasurementHorizontal(item);
            b = t + getDecoratedMeasurementVertical(item);

            float currentScale = 0f;
            float offset = i - mFirstVisiPos - fraction;
            float transY = 0f;
            float alpha = 1f;

            if (mFirstVisiPos == i) {
                // 缩放子view
                final float minScale = 0.6f;
                final int childCenterX = (r + l) / 2;
                final int parentCenterX = getWidth() / 2;
                isChildLayoutLeft = childCenterX <= parentCenterX;
                if (isChildLayoutLeft) {
                    final float fractionScale = (parentCenterX - childCenterX) / (parentCenterX * 1.0f);
                    currentScale = 1.0f - (1.0f - minScale) * fractionScale;
                } else {
                    final float fractionScale = (childCenterX - parentCenterX) / (parentCenterX * 1.0f);
                    currentScale = 1.0f - (1.0f - minScale) * fractionScale;
                }
                transY = (mLastVisiPos - i + 1 - offset) * normalViewTrans;
            } else {
                currentScale = (float) Math.pow(normalViewScale, offset);
                alpha = (float) Math.pow(normalViewAlpha, offset);
                //因为缩放了Y轴，所以需要将缩小的Y值加上去
                transY = (mLastVisiPos - i + 1 - offset) * normalViewTrans - getHeight() * (1 - currentScale) / 2;
            }
            item.setScaleX(currentScale);
            item.setScaleY(currentScale);
            item.setTranslationY(transY);
            item.setAlpha(alpha);

            layoutDecoratedWithMargins(item, l, t, r, b);

        }

        return dx;
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
//        Log.d(TAG, "onScrollStateChanged: state = " + state);
        switch (state) {
            case RecyclerView.SCROLL_STATE_DRAGGING:
                //当手指按下时，停止当前正在播放的动画
                cancelAnimator();
                perMoveOffsetX = 0;
                break;
            case RecyclerView.SCROLL_STATE_IDLE:
                //当列表滚动停止后，判断一下自动选中是否打开
                if (isAutoSelect) {
                    //找到离目标落点最近的item索引
                    smoothScrollToPosition(findShouldSelectPosition());
                }
                break;
            default:
                break;
        }
    }

    public int findShouldSelectPosition() {
        if (onceCompleteScrollLength == -1 || mFirstVisiPos == -1) {
            return -1;
        }
        int position = (int) (Math.abs(mHorizontalOffset) / childWidth);
        int remainder = (int) (Math.abs(mHorizontalOffset) % childWidth);
//        Log.d(TAG, "findShouldSelectPosition: remainder = " + remainder + " perMoveOffsetX = " + perMoveOffsetX + " position = " + position);

        if (perMoveOffsetX >= 0 && remainder > childWidth / 6.0f) {    //向左滑动
            if (position + 1 <= getItemCount() - 1) {
                return position + 1;
            }
        } else if (perMoveOffsetX < 0 && remainder >= childWidth * 3 / 4.0f) { //向右滑动
            if (position + 1 <= getItemCount() - 1) {
                return position + 1;
            }
        }
        return position;
    }

    /**
     * 平滑滚动到某个位置
     *
     * @param position 目标Item索引
     */
    public void smoothScrollToPosition(int position) {
        if (position > -1 && position < getItemCount()) {
            startValueAnimator(position);
        }
    }

    private void startValueAnimator(int position) {
        cancelAnimator();

        final float distance = getScrollToPositionOffset(position);

        long minDuration = 100;
        long maxDuration = 300;
        long duration;

        float distanceFraction = (Math.abs(distance) / childWidth);

        if (distance <= childWidth) {
            duration = (long) (minDuration + (maxDuration - minDuration) * distanceFraction);
        } else {
            duration = (long) (maxDuration * distanceFraction);
        }
        selectAnimator = ValueAnimator.ofFloat(0.0f, distance);
        selectAnimator.setDuration(duration);
        selectAnimator.setInterpolator(new LinearInterpolator());
        final float startedOffset = mHorizontalOffset;
        selectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mHorizontalOffset = (long) (startedOffset + value);
                requestLayout();
            }
        });
        selectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (onCardSelectedListener != null) {
                    onCardSelectedListener.onCardSelected(position);
                }
            }
        });
        selectAnimator.start();
    }

    /**
     * @param position
     * @return
     */
    private float getScrollToPositionOffset(int position) {
        return (long) childWidth * position - Math.abs(mHorizontalOffset);
    }

    /**
     * 取消动画
     */
    public void cancelAnimator() {
        if (selectAnimator != null && (selectAnimator.isStarted() || selectAnimator.isRunning())) {
            selectAnimator.cancel();
        }
    }

    /**
     * 回收需回收的item
     */
    private void recycleChildren(RecyclerView.Recycler recycler) {
        List<RecyclerView.ViewHolder> scrapList = recycler.getScrapList();
        for (int i = 0; i < scrapList.size(); i++) {
            RecyclerView.ViewHolder holder = scrapList.get(i);
            removeAndRecycleView(holder.itemView, recycler);
        }
    }

    /**
     * 获取某个childView在水平方向所占的空间，将margin考虑进去
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementHorizontal(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin
                + params.rightMargin;
    }

    /**
     * 获取某个childView在竖直方向所占的空间,将margin考虑进去
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin
                + params.bottomMargin;
    }

    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public void setOnCardSelectedListener(OnCardSelectedListener onCardSelectedListener) {
        this.onCardSelectedListener = onCardSelectedListener;
    }

    /**
     * 继承 ScrollVectorProvider并配合PagerSnapHelper实现，每次只翻一页
     *
     * @param targetPosition targetPosition
     * @return offset
     */
    @Nullable
    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        float targetX = childWidth * targetPosition;
        float offset = targetX - mHorizontalOffset;
//        Log.d(TAG, "computeScrollVectorForPosition: targetPosition = " + targetPosition + " targetX = " + targetX + " mHorizontalOffset = "+mHorizontalOffset );
        return new PointF(offset, 0);
    }

    public interface OnCardSelectedListener {
        void onCardSelected(int position);
    }
}
