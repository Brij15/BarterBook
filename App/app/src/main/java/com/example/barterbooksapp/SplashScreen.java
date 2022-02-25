package com.example.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Remove action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
//                or try this in manifest
//                android:noHistory="true"
            }
        };

        Handler h = new Handler();
        // The Runnable will be executed after the given delay time
        h.postDelayed(r, 2000); // will be delayed for 2 seconds
    }
}