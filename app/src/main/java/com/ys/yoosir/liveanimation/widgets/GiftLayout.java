package com.ys.yoosir.liveanimation.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.ys.yoosir.liveanimation.R;
import com.ys.yoosir.liveanimation.model.BaseGiftBean;
import com.ys.yoosir.liveanimation.utils.AbstractGiftPathAnimator;
import com.ys.yoosir.liveanimation.utils.GiftPathAnimator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** 送礼物动画
 * Created by ys on 2016/8/30 0030.
 */
public class GiftLayout extends LinearLayout implements GiftCustomLayout.GiftAnimationListener{

    private AttributeSet attrs = null;
    private int defStyleAttr = 0;

    private AbstractGiftPathAnimator mAnimator;
    private static GiftHandler mGiftHandler;
    private static GiftThread mGiftThread;

    private static ArrayList<BaseGiftBean> giftMessageQueue = new ArrayList<>();
    private static ArrayList<String> giftKeyQueue = new ArrayList<>();
    private static Map<String,BaseGiftBean> giftAnimationQueue = new HashMap<>();
    private static Map<String,GiftCustomLayout>     giftViewQueue = new HashMap<>();

    public GiftLayout(Context context) {
        this(context,null);
    }

    public GiftLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GiftLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
        findViewById(context);
    }

    private void findViewById(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }

    private void init(AttributeSet attrs, int defStyleAttr){
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GiftLayout,defStyleAttr,0);
        mAnimator = new GiftPathAnimator(AbstractGiftPathAnimator.Config.fromTypeArray(a,0,0,0,600,200));
        a.recycle();
    }

    /** 添加礼物 **/
    public void addGift(){
        GiftCustomLayout customLayout = new GiftCustomLayout(getContext(),this);
        View giftCustomView = customLayout.createGiftCustomLayout(null,1);
        init(attrs,defStyleAttr);
        mAnimator.start(giftCustomView,this);
    }

    /**  添加礼物
     *  @param giftBean 礼物信息
     **/
    public void addGift(BaseGiftBean giftBean){
        if(giftBean == null) return;
        String key = giftBean.getSenderId()+"_"+giftBean.getGiftType();
        if(giftAnimationQueue.containsKey(key)){
            giftAnimationQueue.get(key).addGiftCounts(giftBean.getGiftCounts());
        }else{
            if(giftKeyQueue.contains(key)){
                int index = giftKeyQueue.indexOf(key);
                giftMessageQueue.get(index).addGiftCounts(giftBean.getGiftCounts());
            }else{
                giftKeyQueue.add(key);
                giftMessageQueue.add(giftBean);
            }
        }
        //打开动画线程
        openGiftThread();
    }

    /**
     *  开启动画线程
     */
    private synchronized void openGiftThread(){
        if(giftAnimationQueue.size() < 2 && giftKeyQueue.size() > 0){
            giftAnimationQueue.put(giftKeyQueue.get(0),giftMessageQueue.get(0));
            showGiftAnimation(giftKeyQueue.get(0),giftMessageQueue.get(0));
            giftKeyQueue.remove(0);
            giftMessageQueue.remove(0);
        }
    }

    /**
     *  显示 礼物动画
     * @param giftBean 礼物信息
     */
    private void showGiftAnimation(String key,BaseGiftBean giftBean){
        GiftCustomLayout customLayout = new GiftCustomLayout(getContext(),this);
        View giftCustomView = customLayout.createGiftCustomLayout(giftBean,1);
        giftViewQueue.put(key,customLayout);
        init(attrs,defStyleAttr);
        mAnimator.start(giftCustomView,this);
    }

    private long nowTime,lastTime;
    final static int[] sizeTable = {9,99,999,9999,99999,999999,9999999,99999999,999999999,Integer.MAX_VALUE};
    public static int sizeOfInt(int x){
        for (int i = 0; ; i++) {
            if(x <= sizeTable[i]){
                return i + 1;
            }
        }
    }

    public void addGift(int size){
        switch (sizeOfInt(size)){
            case 1:
                size = size % 10;
                break;
            default:
                size = size % 100;
        }
        if(size == 0) return;
        nowTime = System.currentTimeMillis();
        long time = nowTime - lastTime;
        if(lastTime == 0)
            time = 2 * 1000;//第一次分为2秒显示完
        time = time / (size + 15);
        if(mGiftThread == null){
            mGiftThread = new GiftThread();
        }
        if(mGiftHandler == null){
            mGiftHandler = new GiftHandler(this);
            mGiftHandler.post(mGiftThread);
        }
        mGiftThread.addTask(time,size);
        lastTime = nowTime;
    }

    /** 清除动画效果 **/
    public void clean(){
        if(mGiftThread != null){
            mGiftThread.clean();
        }
    }

    /**  销毁动画效果 **/
    public void release(){
        if(mGiftHandler != null){
            mGiftHandler.removeCallbacks(mGiftThread);
            mGiftThread = null;
            mGiftHandler = null;
        }
    }

    @Override
    public void giftAnimationEnd(BaseGiftBean giftBean) {
        //动画走完了
        //TODO 消失动画
    }

    public class GiftHandler extends Handler{
        public final static int MSG_SHOW = 1;
        private WeakReference<GiftLayout> wr;

        public GiftHandler(GiftLayout layout){
            wr = new WeakReference<GiftLayout>(layout);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GiftLayout layout = wr.get();
            if(layout == null) return;
            switch (msg.what){
                case MSG_SHOW:
                    layout.addGift();
                    break;
                default:
                    break;
            }
        }
    }

    public class GiftThread implements Runnable{

        private long time = 0;
        private int allSize = 0;

        public void addTask(long time, int size){
            this.time = time;
            allSize += size;
        }

        public void clean(){
            allSize = 0;
        }

        @Override
        public void run() {
            if(mGiftHandler == null) return;
            if(allSize > 0){
                mGiftHandler.sendEmptyMessage(GiftHandler.MSG_SHOW);
                allSize--;
            }
            postDelayed(this,time);
        }
    }
}
