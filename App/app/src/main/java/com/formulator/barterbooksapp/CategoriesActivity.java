package com.formulator.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.formulator.barterbooksapp.R;
import com.formulator.barterbooksapp.recyclerViewAdapters.LocCatListRecyclerView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String[] categoriesList;
    private LocCatListRecyclerView adapter;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        mAuth = FirebaseAuth.getInstance();

        categoriesList = getResources().getStringArray(R.array.book_categories);

        recyclerView = findViewById(R.id.categoriesRecyclerView);
        adapter = new LocCatListRecyclerView(this, Arrays.asList(categoriesList));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

    }
}