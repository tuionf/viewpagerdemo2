package com.example.tuionf.viewpagerdemo2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ViewPager mViewPager;
    private List<View> mViews;
    private LinearLayout ll;
    private RelativeLayout rl;
    private View red_point;
    private int distance;
    int XUNHUAN = 11;
    private Handler lunbotuHandler;
    private Runnable lunbotuRunnable ;
    private int current = 0;
    //    private int currentIndex = 0;

    private static final String TAG = "MainActivity";
    //图片资源  数组
    //多放置两张图片 为了展示轮播效果
    int [] img = new int[]{R.drawable.guaid_4,R.drawable.guaid_0,R.drawable.guaid_1,R.drawable.guaid_2,R.drawable.guaid_3,R.drawable.guaid_4,R.drawable.guaid_0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ll = (LinearLayout) findViewById(R.id.ll);
        //        rl = (RelativeLayout) findViewById(R.id.rl);
        //        red_point =  findViewById(R.id.red_point);

        lunbotuHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 222 ){
                    //等的时候 说明 已经到了最后一张
                    if (current == img.length -2){
                        current = 1;
                    }else {
                        current++;
                    }
                    //True可平滑滚动到新项目，false可立即转换
                    mViewPager.setCurrentItem(current,true);
                }else {

                }

                lunbotuHandler.postDelayed(lunbotuRunnable,2000);
            }
        };

        lunbotuRunnable = new Runnable() {
            @Override
            public void run() {
                Message msg = lunbotuHandler.obtainMessage();
                msg.what = 222;
                lunbotuHandler.sendMessage(msg);
            }
        };

        lunbotuHandler.postDelayed(lunbotuRunnable,2000);
        initData();
        initPoint();
        mViewPager.setCurrentItem(1);

    }

    /*
    * 添加图片资源  初始化数据
    * */
    private void initData(){
        setViewPagerScroll();
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

    /*
    * 初始化小点
    * */
    private void initPoint(){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
        for (int i = 0; i < (img.length-2); i++) {
            View v = new View(this);
            v.setBackgroundResource(R.drawable.hey_point);
            //设置边距
            params.setMargins(5,0,5,0);
            v.setLayoutParams(params);
            if(i==0) {
                v.setBackgroundResource(R.drawable.red_point);
            }else {
                v.setBackgroundResource(R.drawable.hey_point);
            }
            ll.addView(v);
        }

        //        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) red_point.getLayoutParams();
        //        params1.setMargins(50,0,0,0);
        //        Log.d(TAG, "onPageScrolled: "+ params1.leftMargin);
        //        red_point.setLayoutParams(params1);

    }

    /**
     * 通过反射设置viewpager的轮播速度
     * */
    private void setViewPagerScroll(){
        Field mScroller = null;
        try {
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller viewPagerScroller = new ViewPagerScroller(MainActivity.this);
            mScroller.set(mViewPager,viewPagerScroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        //        red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        //            @Override
        //            public void onGlobalLayout() {
        //                distance =  ll.getChildAt(1).getLeft() - ll.getChildAt(0).getLeft();
        //                Log.d(TAG, distance + "测出来了");
        //            }
        //        });
        //        Log.d(TAG , "onPageScrolled "+ position+"---当前页面偏移的百分比"
        //                +positionOffset+"当前页面偏移的像素位置"+positionOffsetPixels);
        //        float left = distance * ( position + positionOffset );
        //        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) red_point.getLayoutParams();
        //        params.leftMargin = Math.round(left);
        //        Log.d(TAG, "onPageScrolled: "+ params.leftMargin);
        //        red_point.setLayoutParams(params);

        //        LinearLayout.LayoutParams mp = (LinearLayout.LayoutParams) ll.getChildAt(0).getLayoutParams();
        //        Log.d(TAG, "onPageScrolled1: "+mp.leftMargin);
        //        mp.leftMargin = 20;
        //        red_point.setLayoutParams(mp);

    }

    // viewpager 和小点的位置是差1的
    @Override
    public void onPageSelected(int position) {

        //当前位置是 0 的时候，应该显示最后一张，造成轮播的假象
        if (position < 1){
            mViewPager.setCurrentItem(img.length-2,true);
            current = img.length - 2;

            //当前位置是 最后一张 的时候，应该显示第一张（不是第零张），造成轮播的假象
        }else if (position > (img.length-2)){
            mViewPager.setCurrentItem(1,true);
            current = 1;
        }

        setPointSelected(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //position 是当前选中的viewpager的位置
    private void setPointSelected(int position){

        for (int i = 0; i < (img.length-2) ; i++) {
            if (i+1 == position){
                ll.getChildAt(i).setBackgroundResource(R.drawable.red_point);

            }else {
                ll.getChildAt(i).setBackgroundResource(R.drawable.hey_point);
            }

            if (position == 0){
                ll.getChildAt(img.length - 3).setBackgroundResource(R.drawable.red_point);
            }
            if (position == img.length-1){
                ll.getChildAt(0).setBackgroundResource(R.drawable.red_point);
            }
        }

        current = position - 1;
    }
}
