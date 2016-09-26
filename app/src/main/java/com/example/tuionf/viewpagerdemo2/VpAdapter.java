package com.example.tuionf.viewpagerdemo2;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by tuionf on 2016/9/25.
 */

public class VpAdapter extends PagerAdapter {

    private List<View> mViews;

    public VpAdapter(List<View> views) {
        mViews = views;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }
}
