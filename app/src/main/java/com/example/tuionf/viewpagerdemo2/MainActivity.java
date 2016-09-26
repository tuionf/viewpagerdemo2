package com.example.tuionf.viewpagerdemo2;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ViewPager mViewPager;
    private List<View> mViews;
    private LinearLayout ll;
    private RelativeLayout rl;
    private View red_point;
    private int distance;
//    private int currentIndex = 0;

    private static final String TAG = "MainActivity";
    int [] img = new int[]{R.drawable.guaid_0,R.drawable.guaid_1,R.drawable.guaid_2,R.drawable.guaid_3,R.drawable.guaid_4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ll = (LinearLayout) findViewById(R.id.ll);
//        rl = (RelativeLayout) findViewById(R.id.rl);
        red_point =  findViewById(R.id.red_point);
        initData();
        initPoint();
    }

    private void initData(){

        mViews = new ArrayList<View>();

        VpAdapter vpAdapter = new VpAdapter(mViews);
        for (int i = 0; i < img.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(img[i]);
            mViews.add(iv);
        }

        mViewPager.setAdapter(vpAdapter);
        mViewPager.setOnPageChangeListener(this);

    }

    private void initPoint(){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
        for (int i = 0; i < img.length; i++) {
            View v = new View(this);
            v.setBackgroundResource(R.drawable.hey_point);
            if (i == 0){
                params.leftMargin = 0;
            }else {
                params.leftMargin = 20;
            }
            v.setLayoutParams(params);
            ll.addView(v);
        }

        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) red_point.getLayoutParams();
        params1.setMargins(50,0,0,0);
        Log.d(TAG, "onPageScrolled: "+ params1.leftMargin);
        red_point.setLayoutParams(params1);


//        int[] location = new int[2];
//        ll.getChildAt(0).getLocationInWindow(location);
//        Log.d(TAG, "initPoint: location[0]"+location[0]+"location[1]"+location[1]);
//        red_point.setX(location[0]);
//        red_point.setY(location[1]);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                distance =  ll.getChildAt(1).getLeft() - ll.getChildAt(0).getLeft();
                Log.d(TAG, distance + "测出来了");
            }
        });
        Log.d(TAG , "onPageScrolled "+ position+"---当前页面偏移的百分比"
                +positionOffset+"当前页面偏移的像素位置"+positionOffsetPixels);
        float left = distance * ( position + positionOffset );
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) red_point.getLayoutParams();
        params.leftMargin = Math.round(left);
        Log.d(TAG, "onPageScrolled: "+ params.leftMargin);
        red_point.setLayoutParams(params);

//        LinearLayout.LayoutParams mp = (LinearLayout.LayoutParams) ll.getChildAt(0).getLayoutParams();
//        Log.d(TAG, "onPageScrolled1: "+mp.leftMargin);
//        mp.leftMargin = 20;
//        red_point.setLayoutParams(mp);

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
