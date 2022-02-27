package com.example.barterbooksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> titles;
    private List<Integer> images;
    private List<String> authors;
    private List<String> conditions;
    private List<String> locations;
    private List<String> prices;

    private MyRecyclerViewAdapter adapter;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelected(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.seller_list:
                        intent = new Intent(MainActivity.this, SellerListActivity.class);
                        startActivity(intent);
//                        Not working Issue with Seller list Activity
                        break;

                    case R.id.userSettings:
                        intent = new Intent(MainActivity.this, SplashScreen.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });



        recyclerView = findViewById(R.id.recyclerView);

        titles = new ArrayList<>();
        images = new ArrayList<>();
        authors = new ArrayList<>();
        conditions = new ArrayList<>();
        locations = new ArrayList<>();
        prices = new ArrayList<>();


        images = initializeTestImages(images);
        titles = initializeTestTitles(titles);

        initializeOtherTitles();

        adapter = new MyRecyclerViewAdapter(this, titles, images, authors, conditions, locations, prices);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

    }

    private List<Integer> initializeTestImages(List<Integer> imagesList){
        //initialize images TEST only
        imagesList.add((R.drawable.book1));
        imagesList.add((R.drawable.book2));
        imagesList.add((R.drawable.book3));
        imagesList.add((R.drawable.book4));
        imagesList.add((R.drawable.book5));
        imagesList.add((R.drawable.book6));
        imagesList.add((R.drawable.book7));
        imagesList.add((R.drawable.book8));

        return imagesList;
    }

    private List<String> initializeTestTitles(List<String> titlesList){
        //initialize images TEST only
        titlesList.add("Gardens Of The moon");
        titlesList.add("Algebra and Geometry");
        titlesList.add("Mathematics and The Real World");
        titlesList.add("Fifth Season");
        titlesList.add("Name of the Wind");
        titlesList.add("What IF");
        titlesList.add("Deep Learning With Python");
        titlesList.add("Wise Man's Fear");

        return titlesList;
    }

    private void initializeOtherTitles(){
        //initialize others for testing TEST only
        authors.add("Steven Erikson");
        authors.add("Mark V. Lawson");
        authors.add("Zvi Artstein");
        authors.add("N. K Jemsin");
        authors.add("Patrick Rothfuss");
        authors.add("Randall Munroe");
        authors.add("François Chollet");
        authors.add("Patrick Rothfuss");


        conditions.add("Used Like New");
        conditions.add("Library");
        conditions.add("Used");
        conditions.add("New");
        conditions.add("Used Like New");
        conditions.add("Used Like New");
        conditions.add("Used");
        conditions.add("New");


        locations.add("Barrie");
        locations.add("Barrie");
        locations.add("Barrie");
        locations.add("Barrie");
        locations.add("Barrie");
        locations.add("Barrie");
        locations.add("Barrie");
        locations.add("Barrie");


        prices.add("$8.99");
        prices.add("$4.99");
        prices.add("$6.00");
        prices.add("$12.99");
        prices.add("$10.00");
        prices.add("$6.50");
        prices.add("$9.00");
        prices.add("$14.00");

    }
}