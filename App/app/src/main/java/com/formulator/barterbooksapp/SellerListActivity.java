package com.formulator.barterbooksapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formulator.barterbooksapp.R;
import com.formulator.barterbooksapp.utlity.BookPostDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SellerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private List<BookPostDataModel> bookPosts ;


    private SellerRecycleViewAdapter adapter;
    private FirebaseAuth mAuth;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_list_main);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();

        //Remove action bar
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(SellerListActivity.this, LoginPageActivity.class));
            finish();
        }

        bottomNavigationView = findViewById(R.id.sellerBottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.seller_list);
        bottomNavigationView.getMenu().removeItem(R.id.searchPosts);
        bottomNavigationView.setOnItemSelectedListener(item -> {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.go_home:
                        intent = new Intent(SellerListActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.userSettings:
                        intent = new Intent(SellerListActivity.this, EditUserActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.add_post:
                        intent = new Intent(SellerListActivity.this, PostAdActivity.class);
                        startActivity(intent);
                        break;

                }
                return true;
        });

        recyclerView = findViewById(R.id.sellerRecycleView);

        bookPosts = new ArrayList<>();
        getAllPostsForUser();
        //adapter = new SellerRecycleViewAdapter(this, titles, authors, images);
        adapter = new SellerRecycleViewAdapter(this, bookPosts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
    }

    public void getAllPostsForUser(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("BarterBooksDB")
                .whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String postID =  document.getId();
                                BookPostDataModel post = document.toObject(BookPostDataModel.class);
                                post.setImage(R.drawable.default_book);
                                post.setPostID(postID);
                                Log.i("DBTEST", postID);
                                bookPosts.add(post);
                                adapter.notifyItemInserted(bookPosts.size() - 1);
                            }

                        } else {
                            Log.i("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
