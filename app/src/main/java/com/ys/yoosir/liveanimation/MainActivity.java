package com.ys.yoosir.liveanimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ys.yoosir.liveanimation.ui.ShowGiftActivity;
import com.ys.yoosir.liveanimation.ui.ShowStarActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_star_animation).setOnClickListener(this);
        findViewById(R.id.btn_gift_animation).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_star_animation:
                startActivity(new Intent(this, ShowStarActivity.class));
                break;
            case R.id.btn_gift_animation:
                startActivity(new Intent(this, ShowGiftActivity.class));
                break;
        }
    }
}
