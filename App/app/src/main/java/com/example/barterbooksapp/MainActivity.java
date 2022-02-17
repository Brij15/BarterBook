package com.example.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}