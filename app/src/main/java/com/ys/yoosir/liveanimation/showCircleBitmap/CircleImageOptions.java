package com.ys.yoosir.liveanimation.showCircleBitmap;

import android.graphics.Color;

import com.cyc.app.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * Created by tina on 16/3/30.
 */
public class CircleImageOptions {

    private static DisplayImageOptions options;

    public static DisplayImageOptions getOptions(){
        if (options == null){
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.new_user_icon)
                    .showImageForEmptyUri(R.drawable.new_user_icon)
                    .showImageOnFail(R.drawable.new_user_icon)
                    .displayer(new SimpleBitmapDisplayer())
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .displayer(new CircleBitmapDisplayer(Color.TRANSPARENT, 5))
                    .build();
        }
        return options;
    }

}
