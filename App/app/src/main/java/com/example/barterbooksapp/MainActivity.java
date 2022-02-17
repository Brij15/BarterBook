package com.example.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> titles;
    private List<String> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            recyclerView = findViewById(R.id.recyclerView);
        }

        titles = new ArrayList<>();
        images = new ArrayList<>();
    }
}