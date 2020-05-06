package com.slin.study.kotlin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.Objects;

/**
 * 网格分割线
 * <p>
 * Created by slin on 2020/3/30
 */
public class GridDividerItemDivider extends RecyclerView.ItemDecoration {

    private static final String TAG = GridDividerItemDivider.class.getSimpleName();

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;

    public GridDividerItemDivider(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this "
                    + "DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        a.recycle();
    }

    public GridDividerItemDivider(int verticalSpan, int horizontalSpan, @ColorInt int color) {
        RectShape rectShape = new RectShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
        shapeDrawable.getPaint().setColor(color);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.setIntrinsicWidth(horizontalSpan);
        shapeDrawable.setIntrinsicHeight(verticalSpan);
        mDivider = shapeDrawable;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public void setDrawable(@NonNull Drawable drawable) {
        mDivider = drawable;
    }


    /**
     * 这里返回的是outRect，相当于给item添加一个outRect的上下左右大小的padding
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int childCount = Objects.requireNonNull(parent.getAdapter(), "Please set adapter first").getItemCount();
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        if (itemPosition < 0) {
            return;
        }
        //item处于哪一列
        int whichColumn = itemPosition % spanCount;
        int halfWidth = Math.round(mDivider.getIntrinsicWidth() / 2.0f);
        //第一列左边距为0，其他左边都为宽度的一半
        int left = whichColumn > 0 ? halfWidth : 0;
        //最后一列右边距为0，其余右边距为宽度的一半
        int right = (whichColumn + 1) != spanCount ? halfWidth : 0;
        int bottom = 0;
        //除了最后一行，底部边距都为图片高度
        if (!isLastRow(parent, itemPosition, spanCount, childCount)) {
            bottom = mDivider.getIntrinsicHeight();
        }
        outRect.set(left, 0, right, bottom);
    }

    /**
     * 这个方法绘制的会覆盖在item上面，可以用来在item上面绘制一些标签什么的
     */
    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
    }

    /**
     * 这个方法绘制的信息在item下面
     */
    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        drawVertical(canvas, parent);
        drawHorizontal(canvas, parent);
    }

    /**
     * 绘制水平分割线
     *
     * @param canvas canvas
     * @param parent RecyclerView
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            //最后一行不绘制底部
            if (isLastRow(parent, i, getSpanCount(parent), childCount)) {
                continue;
            }
            //绘制时留出 Margin
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin;
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicWidth();

//            LogUtils.d("drawHorizontal left: %d top:%d right: %d bottom: %d params: %d %d %d %d", left, top, right, bottom,
//                    right - left, params.topMargin, params.rightMargin, params.bottomMargin);
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }

    }

    /**
     * 绘制垂直分割线
     *
     * @param canvas canvas
     * @param parent RecyclerView
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (parent.getChildViewHolder(child).getAdapterPosition() % spanCount + 1 == spanCount) {
                continue;
            }
            //绘制时留出 Margin
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - params.leftMargin;
            int bottom = child.getBottom() + params.bottomMargin + mDivider.getIntrinsicHeight();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();

//            LogUtils.d("drawHorizontal left: %d top:%d right: %d bottom: %d params: %d %d %d %d", left, top, right, bottom,
//                    right - left, params.topMargin, params.rightMargin, params.bottomMargin);
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    /**
     * 获取列数
     *
     * @param parent RecyclerView
     * @return 列数
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    /**
     * 判断是否是最后一行
     *
     * @return true 最后一行
     */
    private boolean isLastRow(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            return isLastRow(pos, spanCount, childCount);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) manager).getOrientation() == StaggeredGridLayoutManager.VERTICAL) {
                return isLastRow(pos, spanCount, childCount);
            } else {
                //如果是横向布局的话，那么 pos+1 是列数的倍数即是最后一行
                return (pos + 1) % spanCount == 0;
            }
        }
        return false;
    }

    private boolean isLastRow(int pos, int spanCount, int childCount) {
        //取模，取出最后一行的item数量
        int remainCount = childCount % spanCount;

        //最后一行等于0的话，正好显示完整，那么判断最后一行的位置大于 总数 - 总列数，否则 大于 总数 - 最后一行数量
        if (remainCount == 0) {
            return pos >= childCount - spanCount;
        } else {
            return pos >= childCount - remainCount;
        }

    }

}
