package com.example.barterbooksapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterbooksapp.recyclerViewAdapters.MyRecyclerViewAdapter;
import com.example.barterbooksapp.utlity.BookPostDataModel;
import com.example.barterbooksapp.utlity.FilterUtilities;
import com.facebook.login.LoginManager;
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


    @SuppressLint("NonConstantResourceId")
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


        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.go_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()){
                case R.id.seller_list:
                    intent = new Intent(MainActivity.this, SellerListActivity.class);
                    startActivity(intent);
                    break;

                case R.id.userSettings:
                    intent = new Intent(MainActivity.this, EditUserActivity.class);
                    startActivity(intent);
                    break;

                case R.id.add_post:
                    intent = new Intent(MainActivity.this, PostAdActivity.class);
                    startActivity(intent);
                    break;

                case R.id.searchPosts:
                    searchPosts();
                    break;
            }
            return false;
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
        categorySelect.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
            startActivity(intent);
        });

        locationSelect = findViewById(R.id.locationSelect);
        locationSelect.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            startActivity(intent);
        });

    }

    private List<BookPostDataModel> filterBy(String filterValue, FilterType type){
        return FilterUtilities.filterBy(filterValue, type, bookPosts);
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

    @SuppressLint("NotifyDataSetChanged")
    private void searchPosts(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.search_input, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(true);
        final EditText searchTextView =  myView.findViewById(R.id.search_title);
        final Button cancel = myView.findViewById(R.id.cancel);
        final Button search = myView.findViewById(R.id.search);

        search.setOnClickListener(view -> {
            String searchText = searchTextView.getText().toString();

            if (TextUtils.isEmpty(searchText)){
                searchTextView.setError("You Need to type something");
                return;
            }
            String replacedStr = searchText.replaceAll("\\s", "");
            if(replacedStr.isEmpty()){
                searchTextView.setError("You Need to type something");
                return;
            }
            else{
                adapter.setItems(getSearchData(searchText));
                adapter.notifyDataSetChanged();
            }
            dialog.dismiss();
            bottomNavigationView.setSelectedItemId(R.id.go_home);
        });

        cancel.setOnClickListener(view -> {
            dialog.dismiss();
            bottomNavigationView.setSelectedItemId(R.id.go_home);
        });
        dialog.show();
    }

    private List<BookPostDataModel> getSearchData(String searchText) {
        bookPosts.clear();
        initializeTestData();
        List<BookPostDataModel> filteredList = new ArrayList<>();
        if (!searchText.isEmpty()){
            filteredList = FilterUtilities.getSearchData(searchText, bookPosts);
        }
        else {
            Toast.makeText(this, "The Entered Text Is Empty", Toast.LENGTH_SHORT).show();
        }
        return filteredList;
    }

    public enum FilterType{
        CATEGORY,
        LOCATION,
    }

}