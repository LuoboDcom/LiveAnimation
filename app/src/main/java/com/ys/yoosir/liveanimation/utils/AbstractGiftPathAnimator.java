package com.ys.yoosir.liveanimation.utils;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.view.View;
import android.view.ViewGroup;

import com.ys.yoosir.liveanimation.R;

/** 礼物出现路径
 * Created by ys on 2016/8/31 0031.
 */
public abstract class AbstractGiftPathAnimator {

    protected final Config mConfig;

    public AbstractGiftPathAnimator(Config config){
        mConfig = config;
    }

    public Path createPath(){
        return null;
    }

    public abstract void start(View child, ViewGroup parent);

    public static class Config{
        public int initX;
        public int initY;
        public int giftWidth;
        public int giftHeight;
        public int animLength;
        public int animDuration;

        static public Config fromTypeArray(TypedArray typedArray,float x,float y,int pointY,int giftWidth,int giftHeight){
            Config config = new Config();
            Resources res = typedArray.getResources();
            config.initX = (int) typedArray.getDimension(R.styleable.GiftLayout_gift_initX,x);
            config.initY = (int) typedArray.getDimension(R.styleable.GiftLayout_gift_initY,y);
            config.animLength = (int) typedArray.getDimension(R.styleable.GiftLayout_gift_animLength,
                    res.getDimensionPixelOffset(R.dimen.gift_anim_length));//动画长度
            config.animDuration = typedArray.getInteger(R.styleable.GiftLayout_gift_anim_duration,1000);//持续时间
            config.giftWidth = giftWidth;
            config.giftHeight = giftHeight;
            return config;
        }
    }

}
