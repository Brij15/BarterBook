package com.example.barterbooksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<BookPostDataModel> bookPosts ;
    private MyRecyclerViewAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    private LinearLayout categorySelect;
    private LinearLayout locationSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.go_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.seller_list:
                        intent = new Intent(MainActivity.this, SellerListActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.userSettings:
                        intent = new Intent(MainActivity.this, SplashScreen.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        bookPosts = new ArrayList<>();
        initializeTestData();
        recyclerView = findViewById(R.id.recyclerView);


        adapter = new MyRecyclerViewAdapter(this, bookPosts);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        //Top Bar
        categorySelect = findViewById(R.id.categorySelect);
        categorySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });

        locationSelect = findViewById(R.id.locationSelect);
        locationSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initializeTestData(){
        //initialize images TEST only
        bookPosts.add(new BookPostDataModel("Gardens Of The moon",R.drawable.book1,"Steven Erikson", "Used Like New", "Barrie", 8.99 ));
        bookPosts.add(new BookPostDataModel("Algebra and Geometry", R.drawable.book2,"Mark V. Lawson", "Library", "Barrie", 8.99));
        bookPosts.add(new BookPostDataModel("Mathematics and The Real World",R.drawable.book3 ,"Zvi Artstein", "Used", "Barrie", 4.99));
        bookPosts.add(new BookPostDataModel("Fifth Season",R.drawable.book4,"N. K Jemsin", "Used","Barrie", 6.00 ));
        bookPosts.add(new BookPostDataModel("Name of the Wind", R.drawable.book5,"Patrick Rothfuss", "Used Like New", "Barrie", 12.99));
        bookPosts.add(new BookPostDataModel("What IF", R.drawable.book6,"Randall Munroe", "Used Like New", "Barrie", 10.00));
        bookPosts.add(new BookPostDataModel("Deep Learning With Python", R.drawable.book7 ,"François Chollet", "Used", "Barrie", 6.50));
        bookPosts.add(new BookPostDataModel("Wise Man's Fear", R.drawable.book8, "Patrick Rothfuss", "New", "Barrie", 14.00));
    }

}