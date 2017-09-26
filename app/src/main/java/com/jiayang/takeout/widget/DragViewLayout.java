package com.jiayang.takeout.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by Administrator on 2017/6/15.
 */

public class DragViewLayout extends FrameLayout {

    private ViewDragHelper dragHelper;
    private View dragView;

    private int positionX, positionY;

    private int l, t, r, b;

    public DragViewLayout(Context context) {
        super(context);
        init();
    }

    public DragViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == dragView;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                positionX = dragView.getLeft();
                positionY = dragView.getTop();
                if (releasedChild == dragView) {
                    int endX = 0, endY = positionY;
                    if (positionX > getWidth() / 2 - dragView.getWidth() / 2) {
                        endX = getWidth() - dragView.getWidth();
                    }
                    dragHelper.settleCapturedViewAt(endX, endY);
                    invalidate();
                }
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;

                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - topBound;

                final int newTop = Math.min(Math.max(top, topBound), bottomBound);

                return newTop;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        dragView = findViewById(R.id.stickyBtn);
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        } else {
//            l = dragView.getLeft();
//            t = dragView.getTop();
//            r = dragView.getRight();
//            b = dragView.getBottom();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (l == 0 && t == 0 && r == 0 && b == 0) {
            return;
        }
        dragView.layout(l, t, r, b);
    }
}
