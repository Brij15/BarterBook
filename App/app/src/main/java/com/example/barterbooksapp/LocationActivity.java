package com.example.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.barterbooksapp.recyclerViewAdapters.LocCatListRecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> locationsList;
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

        locationsList = new ArrayList<>();
        initiateLocationsList();
        recyclerView = findViewById(R.id.locationsRecyclerView);
        adapter = new LocCatListRecyclerView(this, locationsList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

    }

    public void initiateLocationsList(){
        locationsList.add("All Locations");
        locationsList.add("Allandale");
        locationsList.add("Aurora");
        locationsList.add("Camelot Square");
        locationsList.add("Crimson Ridge");
        locationsList.add("North Barrie");
        locationsList.add("CollingWood");
        locationsList.add("Orilla");
        locationsList.add("Newmarket");
        locationsList.add("Stanely");
        locationsList.add("Simcoe Country");
        locationsList.add("Sutton");



    }
}