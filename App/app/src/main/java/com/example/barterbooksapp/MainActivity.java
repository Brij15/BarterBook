package com.example.barterbooksapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterbooksapp.recyclerViewAdapters.MyRecyclerViewAdapter;
import com.example.barterbooksapp.utlity.BookPostDataModel;
import com.example.barterbooksapp.utlity.DBUtilities;
import com.example.barterbooksapp.utlity.FilterUtilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Boolean isDataLoading = true;
    private Integer pageNo = 0;


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

        locationText = findViewById(R.id.textViewLocationSelect);
        categoryText = findViewById(R.id.textViewCategorySelect);
        //Do filter
        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("FilterID")){
            if (!thisIntent.getStringExtra("FilterID").isEmpty()){
                String filterValue = thisIntent.getStringExtra("FilterID");
                String filterID = thisIntent.getStringExtra("PageID");
                if (filterValue.equals("Select Your Location") || filterValue.equals("Select Book Category") ){
//                    Do nothing here
                    getAllPosts();
                }
                else {
                    if (filterID.equals("LOCATION")){
                        filterBy(filterValue, FilterType.location);
                        locationText.setText(filterValue);
                    }
                    else if((filterID.equals("CATEGORY"))){
                        filterBy(filterValue, FilterType.category);
                        categoryText.setText(filterValue);
                    }
                }
            }
        }
        else {
            getAllPosts();
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

        //Page nav
        ImageButton prevButton = findViewById(R.id.btnPrevPage);
        ImageButton nextButton = findViewById(R.id.btnNextPage);
        if (pageNo == 0){
            prevButton.setEnabled(false);
        }
        prevButton.setOnClickListener(v -> {
            pageNo++;
        });

        nextButton.setOnClickListener(v -> {
            if (pageNo > 0) {
                pageNo--;
            }

        });


    }

    private void filterBy(String filterValue, FilterType type){
        bookPosts.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("BarterBooksDB")
                .whereEqualTo(String.valueOf(type), filterValue)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("PIC", String.valueOf(bookPosts.size()));
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String postID =  document.getId();
                            BookPostDataModel post = document.toObject(BookPostDataModel.class);
                            post.setImage(R.drawable.default_book);
                            post.setPostID(postID);
                            bookPosts.add(post);
                            adapter.notifyItemInserted(bookPosts.size() - 1);
                        }
                        isDataLoading = false;
                    } else {
                        Log.i("DB", "Error getting documents: ", task.getException());
                    }
                });
    }

//    public void getAllPostsWithLimit(Integer page){
//        isDataLoading = true;
//        Integer limit = 10;
//        Integer skip = page * 10;
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("BarterBooksDB").orderBy("timePosted", Query.Direction.DESCENDING)
//                .limit(10)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            String postID =  document.getId();
//                            BookPostDataModel post = document.toObject(BookPostDataModel.class);
//                            post.setImage(R.drawable.default_book);
//                            post.setPostID(postID);
//                            bookPosts.add(post);
//                            adapter.notifyItemInserted(bookPosts.size() - 1);
//                        }
//                        isDataLoading = false;
//                    } else {
//                        Log.i("DB", "Error getting documents: ", task.getException());
//                    }
//                });
//    }

    public void getAllPosts(){
        isDataLoading = true;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("BarterBooksDB").orderBy("timePosted", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String postID =  document.getId();
                            BookPostDataModel post = document.toObject(BookPostDataModel.class);
                            post.setImage(R.drawable.default_book);
                            post.setPostID(postID);
                            bookPosts.add(post);
                            adapter.notifyItemInserted(bookPosts.size() - 1);
                        }
                        isDataLoading = false;
                    } else {
                        Log.i("DB", "Error getting documents: ", task.getException());
                    }
                });
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
                bookPosts.clear();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("BarterBooksDB")
                        .whereEqualTo("title", searchText)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("PIC", String.valueOf(bookPosts.size()));
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String postID =  document.getId();
                                    BookPostDataModel post = document.toObject(BookPostDataModel.class);
                                    post.setImage(R.drawable.default_book);
                                    post.setPostID(postID);
                                    bookPosts.add(post);
                                    adapter.notifyItemInserted(bookPosts.size() - 1);
                                }
                                isDataLoading = false;
                            } else {
                                Log.i("DB", "Error getting documents: ", task.getException());
                            }
                        });
//                adapter.setItems(getSearchData(searchText));
                adapter.notifyDataSetChanged();
            }
            dialog.dismiss();
            bottomNavigationView.setSelectedItemId(R.id.go_home);
        });

        cancel.setOnClickListener(view -> {
            dialog.dismiss();
            bookPosts.clear();
            getAllPosts();
            bottomNavigationView.setSelectedItemId(R.id.go_home);
        });
        dialog.show();
    }

    private List<BookPostDataModel> getSearchData(String searchText) {
        bookPosts.clear();
        getAllPosts();
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
        category,
        location,
    }

}