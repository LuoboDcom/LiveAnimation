package com.ys.yoosir.liveanimation.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ys.yoosir.liveanimation.R;
import com.ys.yoosir.liveanimation.model.BaseGiftBean;

/** 礼物界面
 * Created by ys on 2016/8/30 0030.
 */
public class GiftView extends View {

    private BaseGiftBean mGiftBean;

    private Bitmap avatarBitmap;
    private Bitmap giftBitmap;
    private String sender;
    private String str = "送主播";
    private int mCounts = 0; //次数
    private int mIndex = 0;//刷的次数

    //赠送者名称画笔
    private Paint mSenderPaint;
    //一般描述画笔
    private Paint mNormalPaint;
    //赠送者头像区域
    private RectF mAvatarRectF;
    //礼物图像区域
    private RectF mGiftRectF;

    private float textSize = 14;
    private float textWidth = 100;
    private int   avatarBitmapSize = 100;
    private int   giftBitmapSize = 120;
    private int   paddingSize = 20;
    private float textStartX =  paddingSize * 2 + avatarBitmapSize;
    private float textPointY1;
    private float textPointY2;
    private float textEndX;

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * fontScale + 0.5f);
    }

    public GiftView(Context context) {
        this(context,null);
    }

    public GiftView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setGiftData(BaseGiftBean giftBean){
        mGiftBean = giftBean;
    }

    private void init(Context context){
        int textSizePx = sp2px(context,textSize);
        int textWidthPx = dp2px(context,textWidth);
        avatarBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_default_user);
        giftBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.gift1);

        sender = "未命名的小仓";

        mSenderPaint = new Paint();
        mSenderPaint.setAntiAlias(true);         //抗锯齿
        mSenderPaint.setColor(Color.RED);        //画笔颜色
        mSenderPaint.setStyle(Paint.Style.FILL); //画笔类型:填充
        mSenderPaint.setTextSize(textSizePx);            //画笔字体大小

        Paint.FontMetrics fontMetrics = mSenderPaint.getFontMetrics();
        float textSizeReal = textSizePx + fontMetrics.bottom;

        //设置 Text 的Y坐标
        textEndX = textStartX + textWidthPx;
        textPointY1 = textSizeReal;
        textPointY2 = textPointY1 * 2;

        mNormalPaint = new Paint(mSenderPaint);
        mNormalPaint.setColor(Color.BLACK);      //画笔颜色

        mAvatarRectF = new RectF(paddingSize,paddingSize,paddingSize+avatarBitmapSize,paddingSize+avatarBitmapSize);
        mGiftRectF = new RectF(textEndX,0,textEndX+giftBitmapSize,giftBitmapSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(800, 400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("GiftView","onDraw");
        canvas.drawBitmap(avatarBitmap,null,mAvatarRectF,null);
        canvas.drawText(sender,textStartX,textPointY1,mSenderPaint);
        canvas.drawText(str,textStartX,textPointY2,mNormalPaint);
        canvas.drawBitmap(giftBitmap,null,mGiftRectF,null);
    }

}
