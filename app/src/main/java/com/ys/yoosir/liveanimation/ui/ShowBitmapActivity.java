package com.ys.yoosir.liveanimation.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.ys.yoosir.liveanimation.R;
import com.ys.yoosir.liveanimation.showCircleBitmap.CircleBitmapDisplayer;

public class ShowBitmapActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private ImageView circleImageView;
    private ImageView rectangleImageView;

    private String url = "http://f.p.cycangcdn.com/1472722332296.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bitmap);
        initImageLoader(this);
        imageView = (ImageView) findViewById(R.id.image_view);
        circleImageView = (ImageView) findViewById(R.id.circle_image_view);
        rectangleImageView = (ImageView) findViewById(R.id.rectangle_image_view);

    }

    private void initImageLoader(Context context) {
        if (!ImageLoader.getInstance().isInited()) {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext()).threadPoolSize(3).defaultDisplayImageOptions(options).build();
            ImageLoader.getInstance().init(config);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                ImageLoader.getInstance().displayImage(url,imageView);
                break;
            case R.id.btn2:
                DisplayImageOptions options1 = new DisplayImageOptions.Builder().displayer(new CircleBitmapDisplayer(Color.rgb(255,0,0),1f)).build();
                ImageLoader.getInstance().displayImage(url,circleImageView,options1);
                break;
            case R.id.btn3:
                DisplayImageOptions options2 = new DisplayImageOptions.Builder().displayer(new CircleBitmapDisplayer(Color.rgb(255,0,0),1f)).build();
                ImageLoader.getInstance().displayImage(url,rectangleImageView,options2);
                break;
        }
    }


}
