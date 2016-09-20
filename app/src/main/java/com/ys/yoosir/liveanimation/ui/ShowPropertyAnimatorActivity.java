package com.ys.yoosir.liveanimation.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.ys.yoosir.liveanimation.R;

import java.util.Random;

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
        findViewById(R.id.btn_custom_evaluator).setOnClickListener(this);
        findViewById(R.id.btn_custom_interpolator).setOnClickListener(this);
        findViewById(R.id.btn_xml).setOnClickListener(this);
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
            case R.id.btn_custom_evaluator:
                customEvaluator();
                break;
            case R.id.btn_custom_interpolator:
                customInterpolator();
                break;
            case R.id.btn_xml:
                xmlAnimator();
                break;
        }
    }

    private void xmlAnimator() {
        // 加载动画
        Animator anim = AnimatorInflater.loadAnimator(this, R.animator.animator_set);
        //设置 缩放的中心点
        animView.setPivotX(0);
        animView.setPivotY(0);
        //显示的调用invalidate
        animView.invalidate();
        anim.setTarget(animView);
        anim.start();
    }

    private void customInterpolator() {
        //自定义 插值器

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator<PointF>(){

            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                // y 方向是一个变速
                PointF point = new PointF();
                point.x = startValue.x + fraction * (endValue.x - startValue.x) ;
                point.y = (float) (startValue.y + Math.pow(fraction , 2) * (endValue.y - startValue.y));
                return point;
            }
        },new PointF(0,0),new PointF(400,1000));
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF point = (PointF) animation.getAnimatedValue();
                animView.setX(point.x);
                animView.setY(point.y);

            }
        });
        valueAnimator.start();
    }

    private void customEvaluator() {
        //自定义 Evaluator

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator<PointF>(){

            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                // y 方向是一个变速
                PointF point = new PointF();
                point.x = startValue.x + fraction * (endValue.x - startValue.x) ;
                point.y = startValue.y + fraction * (endValue.y - startValue.y);
                return point;
            }
        },new PointF(0,0),new PointF(400,1000));
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF point = (PointF) animation.getAnimatedValue();
                animView.setX(point.x);
                animView.setY(point.y);

            }
        });
        valueAnimator.start();
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
