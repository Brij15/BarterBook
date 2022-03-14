package com.example.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    private  static final int SPLASH = 2000;
    Animation animation;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        //Remove action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        imageView = findViewById(R.id.imageView);
        imageView.setAnimation(animation);

        //NOTE Rename to MAINACTIVITY
        Runnable r = () -> {
            startActivity(new Intent(SplashScreen.this, PostADActivity.class));
            finish();
//                or try this in manifest
//                android:noHistory="true"
        };

        Handler h = new Handler();
        // The Runnable will be executed after the given delay time
        h.postDelayed(r, SPLASH); // will be delayed for 2 seconds
    }
}