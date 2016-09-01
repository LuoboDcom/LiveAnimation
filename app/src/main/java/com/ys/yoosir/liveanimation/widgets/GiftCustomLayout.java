package com.ys.yoosir.liveanimation.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.yoosir.liveanimation.R;
import com.ys.yoosir.liveanimation.model.BaseGiftBean;

/** 礼物 view
 * Created by ys on 2016/9/1 0001.
 */
public class GiftCustomLayout{

    private Context  mContext;
    private GiftAnimationListener mListener;
    private BaseGiftBean mGiftData;
    private Handler mHandler;
    private GiftAnimationThread animationThread;

    private View giftCustomLayout;
    private ImageView senderAvatar;
    private ImageView giftIcon;
    private TextView senderNameTv;
    private TextView mGiftCountView;

    private int mCounts = 1; //次数
    private int mIndex = 10;//刷的次数

    public GiftCustomLayout(Context context,GiftAnimationListener animationListener){
        mContext = context;
        mListener = animationListener;
        mHandler = new Handler(Looper.getMainLooper());
        animationThread = new GiftAnimationThread();

        giftCustomLayout = LayoutInflater.from(context).inflate(R.layout.gift_custom_layout,null);
        senderAvatar = (ImageView) giftCustomLayout.findViewById(R.id.sender_avatar_iv);
        giftIcon = (ImageView) giftCustomLayout.findViewById(R.id.gift_icon);
        senderNameTv = (TextView) giftCustomLayout.findViewById(R.id.sender_name_tv);
        mGiftCountView = (TextView) giftCustomLayout.findViewById(R.id.gift_count_view);
    }

    private CharSequence getCountText(String countText){
        return null;
    }

    private void initData() {
        senderAvatar.setImageResource(R.mipmap.icon_default_user);
        giftIcon.setImageResource(R.mipmap.gift1);
        senderNameTv.setText("yoosir");
        mGiftCountView.setText(mContext.getString(R.string.gift_count_text,mCounts));
        mHandler.postDelayed(animationThread,1000l);
    }

    public void updateGiftCountView(int value){
        mGiftCountView.setText(mContext.getString(R.string.gift_count_text,value));
        startScaleAnimation(mGiftCountView);
    }

    private void startScaleAnimation(View v){

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(v, "scaleX",
                1.0f, 2f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(v, "scaleY",
                1.0f, 2f);
        anim1.setDuration(500);
        anim2.setDuration(500);

        ObjectAnimator anim3 = ObjectAnimator.ofFloat(v, "scaleX",
                2.0f, 1.0f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(v, "scaleY",
                2.0f, 1.0f);
        anim3.setDuration(300);
        anim4.setDuration(300);

        /**
         * anim1，anim2 同时执行
         * anim3 ,anim4接着执行
         */
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2);
        animSet.play(anim3).after(anim2);
        animSet.play(anim3).with(anim4);
        animSet.start();

        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("Animation","onAnimationEnd");
                mHandler.post(animationThread);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public View createGiftCustomLayout(BaseGiftBean giftData,int defaultCounts){
        mGiftData = giftData;
        mCounts = defaultCounts;
        initData();
        return giftCustomLayout;
    }

    public void setGiftCounts(int giftCounts){
        //总的礼物数 - 已送出的礼物数 = 未送出的礼物数
        mIndex += (giftCounts - mCounts);
    }

    public class GiftAnimationThread implements Runnable{

        @Override
        public void run() {
            if(mHandler == null) return;
            if(mIndex > 0){
                Log.i("GiftCustomLayout"," is sending ");
                mCounts++;
                updateGiftCountView(mCounts);
                mIndex--;
            }else{
                Log.i("GiftCustomLayout"," is over ");
                if(mListener != null) {
                    mListener.giftAnimationEnd(mGiftData);
                }
            }
        }
    }

    interface GiftAnimationListener{
        void giftAnimationEnd(BaseGiftBean giftBean);
    }
}
