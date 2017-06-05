package com.lindroid.slidingtoast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class SlidingToastActivity extends AppCompatActivity {
    private TextView tvToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_toast);
        tvToast = (TextView) findViewById(R.id.tv_toast);
    }

    public void showSlidingToast(View view) {
        tvToast.setVisibility(View.VISIBLE);
        //设置SlidingToast的信息
        tvToast.setText("这是一条提示信息");
//        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_toast);
//        tvToast.startAnimation(animation);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
////                tvToast.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                tvToast.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        //创建动画集合
        AnimationSet animationSet = new AnimationSet(true);
        //滑入的动画
        TranslateAnimation inAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        inAnimation.setDuration(500);
        inAnimation.setFillAfter(true);
        animationSet.addAnimation(inAnimation);
        //滑出的动画
        TranslateAnimation outAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);
        outAnimation.setDuration(500);
        outAnimation.setFillAfter(true);
        outAnimation.setStartOffset(2000);
        animationSet.addAnimation(outAnimation);
        tvToast.startAnimation(animationSet);
        //动画监听事件
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                tvToast.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
