package com.ys.yoosir.liveanimation.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ys.yoosir.liveanimation.R;

public class ShowPropertyAnimatorActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView animView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_property_animator);

        animView = (ImageView) findViewById(R.id.show_img_iv);
        findViewById(R.id.btn_value_animator).setOnClickListener(this);
        findViewById(R.id.btn_object_animator).setOnClickListener(this);
        findViewById(R.id.btn_animator_set).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_value_animator:
                doAnimatorByValue();
                break;
            case R.id.btn_object_animator:
                doAnimatorByObject();
                break;
            case R.id.btn_animator_set:
                doAnimatorBySet();
                break;
        }
    }

    private void doAnimatorByValue(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f,0f,1f);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animView.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    private void doAnimatorByObject(){
        ObjectAnimator objectAnimator =ObjectAnimator.ofFloat(animView,"alpha",1f,0f,1f);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    private void doAnimatorBySet(){
        //实现 同时透明和缩放 ,随后360翻转
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimator1 =ObjectAnimator.ofFloat(animView,"alpha",1f,0f,1f);
        objectAnimator1.setDuration(2000);
        ObjectAnimator objectAnimator2 =ObjectAnimator.ofFloat(animView,"scaleX",1f,0f,1f);
        objectAnimator2.setDuration(2000);
        ObjectAnimator objectAnimator3 =ObjectAnimator.ofFloat(animView,"scaleY",1f,0f,1f);
        objectAnimator3.setDuration(2000);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(animView,"rotationX",0f,360f);
        objectAnimator4.setDuration(2000);
        animatorSet.playTogether(objectAnimator1,objectAnimator2,objectAnimator3);
        animatorSet.play(objectAnimator4).after(objectAnimator1);
        animatorSet.start();
    }
}
