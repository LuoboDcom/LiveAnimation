package com.ys.yoosir.liveanimation.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ys.yoosir.liveanimation.R;
import com.ys.yoosir.liveanimation.widgets.HeartLayout;

public class ShowStarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_star);
        HeartLayout mHeartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        mHeartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HeartLayout)view).addFavor();
            }
        });
    }
}
