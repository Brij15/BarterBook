package com.example.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> categoriesList;
    private LocCatListRecyclerView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        categoriesList = new ArrayList<>();
        initiateCategoriesList();
        recyclerView = findViewById(R.id.categoriesRecyclerView);
        adapter = new LocCatListRecyclerView(this, categoriesList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

    }

    public void initiateCategoriesList(){
        categoriesList.add("All Categories");
        categoriesList.add("Action and Adventure");
        categoriesList.add("Biographies and Autobiographies");
        categoriesList.add("Business, Finance and Law");
        categoriesList.add("Classics");
        categoriesList.add("Computers");
        categoriesList.add("Essays");
        categoriesList.add("Fantasy and Science Friction");
        categoriesList.add("Graphic Novels");
        categoriesList.add("History");
        categoriesList.add("Literary Friction");
        categoriesList.add("Medical Books");
        categoriesList.add("Mystery");
        categoriesList.add("Personal Development");
        categoriesList.add("Society and Social Sciences");
        categoriesList.add("Short Stories");
        categoriesList.add("Science");
        categoriesList.add("Text Books");


    }
}