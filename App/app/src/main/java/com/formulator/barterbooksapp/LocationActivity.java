package com.formulator.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.formulator.barterbooksapp.R;
import com.formulator.barterbooksapp.recyclerViewAdapters.LocCatListRecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LocationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String[] locationsList;
    private LocCatListRecyclerView adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mAuth = FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(LocationActivity.this, LoginPageActivity.class));
            finish();
        }

        locationsList = getResources().getStringArray(R.array.locations);
        recyclerView = findViewById(R.id.locationsRecyclerView);
        adapter = new LocCatListRecyclerView(this, Arrays.asList(locationsList));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

    }
}