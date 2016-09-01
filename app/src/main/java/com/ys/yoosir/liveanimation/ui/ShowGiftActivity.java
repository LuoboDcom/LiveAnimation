package com.ys.yoosir.liveanimation.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ys.yoosir.liveanimation.R;
import com.ys.yoosir.liveanimation.widgets.GiftLayout;

public class ShowGiftActivity extends AppCompatActivity {

    private GiftLayout giftLayout;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_gift);
        giftLayout = (GiftLayout) findViewById(R.id.gift_layout);
    }

    public void send(View v){
        giftLayout.addGift();

//        ObjectAnimator anim1 = ObjectAnimator.ofFloat(tv, "scaleX",
//                1.0f, 2f);
//        ObjectAnimator anim2 = ObjectAnimator.ofFloat(tv, "scaleY",
//                1.0f, 2f);
//
//        ObjectAnimator anim3 = ObjectAnimator.ofFloat(tv, "scaleX",
//                2.0f, 1.0f);
//        ObjectAnimator anim4 = ObjectAnimator.ofFloat(tv, "scaleY",
//                2.0f, 1.0f);
//
//        /**
//         * anim1，anim2,anim3同时执行
//         * anim4接着执行
//         */
//        AnimatorSet animSet = new AnimatorSet();
//        animSet.play(anim1).with(anim2);
//        animSet.play(anim3).after(anim2);
//        animSet.play(anim3).with(anim4);
//        animSet.setDuration(1000);
//        animSet.start();
    }
}
