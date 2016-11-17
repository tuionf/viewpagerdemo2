package com.example.tuionf.viewpagerdemo2;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by tuionf on 2016/11/16.
 */

public class ViewPagerScroller extends Scroller {

    //设置滑动速度
    private int mDuration = 500;

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    //通过提供起点和行进距离开始滚动。 滚动将使用默认值250毫秒的持续时间。
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
