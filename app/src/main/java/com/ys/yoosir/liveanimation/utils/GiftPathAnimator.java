package com.ys.yoosir.liveanimation.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import java.util.concurrent.atomic.AtomicInteger;

/** 礼物出现路径动画器
 * Created by ys on 2016/8/31 0031.
 */
public class GiftPathAnimator extends AbstractGiftPathAnimator{

    private final AtomicInteger mCounter = new AtomicInteger(0);
    private Handler mHandler;

    public GiftPathAnimator(AbstractGiftPathAnimator.Config config){
        super(config);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void start(View child, ViewGroup parent) {
        parent.addView(child,new ViewGroup.LayoutParams(mConfig.giftWidth,mConfig.giftHeight));
        TranslateAnimation anim = new TranslateAnimation(-mConfig.giftWidth,0,mCounter.get() * mConfig.giftHeight,mCounter.get() * mConfig.giftHeight);
        anim.setDuration(mConfig.animDuration);
        anim.setInterpolator(new LinearInterpolator());
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //礼物总和加1
                mCounter.incrementAndGet();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //礼物动画出现结束
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        child.startAnimation(anim);
    }
}
