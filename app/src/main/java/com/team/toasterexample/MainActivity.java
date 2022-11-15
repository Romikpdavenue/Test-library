package com.team.toasterexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.team.custlibrary.customAds.AdsPlace;

public class MainActivity extends AppCompatActivity {
    AdsPlace adsPlace;
    Button showAds,showAds1;
    RelativeLayout frameBanner,frameBanner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAds=findViewById(R.id.showAds);
        frameBanner=findViewById(R.id.frameBanner);
        frameBanner1=findViewById(R.id.frameBanner1);
        adsPlace=new AdsPlace(MainActivity.this);

//        showAds.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adsPlace.addView(frameBanner);
//                adsPlace.addView(frameBanner1);
//            }
//        });

//        showAds1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adsPlace.addView(frameBanner);
//            }
//        });

    }
}