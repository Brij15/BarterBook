package com.example.barterbooksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<BookPostDataModel> bookPosts ;
    private MyRecyclerViewAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    private LinearLayout categorySelect;
    private LinearLayout locationSelect;
    private DatabaseReference budgetRef;
    private FirebaseAuth mAuth;
    private TextView locationText;
    private TextView categoryText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        //Remove action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(MainActivity.this, LoginPageActivity.class));
            finish();
        }
        budgetRef = FirebaseDatabase.getInstance().getReference().child("testBarterBooks");
        String data = "Hello World";
//        budgetRef.child(new Date().toString()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "Item added successfully", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        budgetRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String value = "";
//
//                for  (DataSnapshot snap: snapshot.getChildren()){
//                    value = String.valueOf(snap.getValue());
//                    Toast.makeText(MainActivity.this, "retrieved " + value, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


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
                        mAuth.signOut();
                        intent = new Intent(MainActivity.this, LoginPageActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.searchPosts:
                        searchPosts();
                        break;
                }

                return true;
            }
        });

        bookPosts = new ArrayList<>();
        initializeTestData();

        locationText = findViewById(R.id.textViewLocationSelect);
        categoryText = findViewById(R.id.textViewCategorySelect);
        //Do filter
        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("FilterID")){
//            Log.i("FilterID", thisIntent.getStringExtra("FilterID"));
            if (!thisIntent.getStringExtra("FilterID").isEmpty()){
                String filterValue = thisIntent.getStringExtra("FilterID");
                String filterID = thisIntent.getStringExtra("PageID");
                if (filterValue.equals("All Locations") || filterValue.equals("All Categories") ){
//                    Do nothing here
                }
                else {
                    if (filterID.equals("LOCATION")){
                        bookPosts = filterBy(filterValue, FilterType.LOCATION);
                        locationText.setText(filterValue);
                    }
                    else if((filterID.equals("CATEGORY"))){
                        bookPosts = filterBy(filterValue, FilterType.CATEGORY);
                        categoryText.setText(filterValue);
                    }
                }
            }
        }

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

    private List<BookPostDataModel> filterBy(String filterValue, FilterType type){
        List<BookPostDataModel> filteredList = new ArrayList<>();
        //currently filter by category or Location not both
        if (type.equals(FilterType.CATEGORY)){
            for(BookPostDataModel item : bookPosts) {
                if (item.getCategory().equals(filterValue)){
                    filteredList.add(item);
                }
            }
        }
        else if(type.equals(FilterType.LOCATION)){
            for(BookPostDataModel item : bookPosts) {
                if (item.getLocation().equals(filterValue)){
                    filteredList.add(item);
                }
            }
        }
        return filteredList;
    }

    private void initializeTestData(){
        //initialize images TEST only
        bookPosts.add(new BookPostDataModel("Gardens Of The moon",R.drawable.book1,"Steven Erikson", "Used Like New", "Aurora", 8.99, "Fantasy and Science Friction" ));
        bookPosts.add(new BookPostDataModel("Algebra and Geometry", R.drawable.book2,"Mark V. Lawson", "Library", "Crimson Ridge", 8.99, "Text Books"));
        bookPosts.add(new BookPostDataModel("Mathematics and The Real World",R.drawable.book3 ,"Zvi Artstein", "Used", "Stanely", 4.99, "Text Books"));
        bookPosts.add(new BookPostDataModel("Fifth Season",R.drawable.book4,"N. K Jemsin", "Used","Crimson Ridge", 6.00 , "Fantasy and Science Friction"));
        bookPosts.add(new BookPostDataModel("Name of the Wind", R.drawable.book5,"Patrick Rothfuss", "Used Like New", "Orilla", 12.99, "Fantasy and Science Friction"));
        bookPosts.add(new BookPostDataModel("What IF", R.drawable.book6,"Randall Munroe", "Used Like New", "CollingWood", 10.00, "Science"));
        bookPosts.add(new BookPostDataModel("Deep Learning With Python", R.drawable.book7 ,"François Chollet", "Used", "CollingWood", 6.50, "Computers"));
        bookPosts.add(new BookPostDataModel("Wise Man's Fear", R.drawable.book8, "Patrick Rothfuss", "New", "Orilla", 14.00, "Fantasy and Science Friction"));
    }

    private void searchPosts(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.search_input, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        final EditText searchTextView =  myView.findViewById(R.id.search_title);
        final Button cancel = myView.findViewById(R.id.cancel);
        final Button search = myView.findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchTextView.getText().toString();

                if (TextUtils.isEmpty(searchText)){
                    searchTextView.setError("You Need to type something");
                    return;
                }

                else{
                    adapter.setItems(getSearchData(searchText));
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private List<BookPostDataModel> getSearchData(String searchText) {
//        do the filtering here
        List<BookPostDataModel> filteredList = new ArrayList<>();
        //currently filter by category or Location not both
        if (!searchText.isEmpty()){
            for(BookPostDataModel item : bookPosts) {
                if (item.getTitle().equals(searchText)){
                    filteredList.add(item);
                }
            }
        }

        return filteredList;
    }

    private  enum FilterType{
        CATEGORY,
        LOCATION,
    }

}