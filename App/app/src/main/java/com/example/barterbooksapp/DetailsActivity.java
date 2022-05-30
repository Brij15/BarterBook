package com.example.barterbooksapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.barterbooksapp.utlity.BookPostDataModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    TextView authorText;
    TextView priceText;
    TextView titleText;
    TextView postDetailText;
    TextView postDescription;
    TextView postCondition;
    TextView postCategory;
    TextView postBarter;
    TextView postLocation;
    ImageSlider imageSlider;
    String userEmail;

    private BottomNavigationView bottomNavigationView;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        titleText = findViewById(R.id.bookdetailTitle);
        authorText = findViewById(R.id.authorDetailText);
        priceText = findViewById(R.id.priceDetailText);
        imageSlider = findViewById(R.id.slider);
        postDetailText = findViewById(R.id.postDetailText);
        postDescription = findViewById(R.id.postDescription);
        postCondition = findViewById(R.id.postCondition);
        postCategory = findViewById(R.id.postCategory);
        postBarter = findViewById(R.id.postBarter);
        postLocation = findViewById(R.id.postLocation);


        Intent thisIntent = getIntent();


        if (thisIntent.hasExtra("postID")){
            if (!thisIntent.getStringExtra("postID").isEmpty()){
                String filterValue = thisIntent.getStringExtra("postID");
                DocumentReference docRef = db.collection("BarterBooksDB").document(filterValue);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        BookPostDataModel book = documentSnapshot.toObject(BookPostDataModel.class);
                        titleText.setText(book.getTitle());
                        authorText.setText(book.getAuthor());
                        priceText.setText(book.getPrice().toString());

                        String pattern = "MM/dd/yyyy HH:mm:ss";
                        DateFormat df = new SimpleDateFormat(pattern);

                        String dateAsString = df.format(book.getTimePosted());
                        postDetailText.setText(dateAsString);
                        postDescription.setText(book.getDetails());

                        postCondition.setText(book.getCondition());
                        postCategory.setText(book.getCategory());
                        postBarter.setText("");
                        postLocation.setText(book.getLocation());
                        userEmail = book.getUserEmail();
                        List<SlideModel> slideModels = new ArrayList<>();
                        if (book.getImagesList() == null){
                            //            Do default image Here
                            slideModels.add(new SlideModel(R.drawable.default_book, ScaleTypes.CENTER_INSIDE));
                        }
                        else {
                            for(String image : book.getImagesList()) {
                                slideModels.add(new SlideModel(image, ScaleTypes.CENTER_INSIDE));
                            }
                        }
                        imageSlider.setImageList(slideModels);
                    }
                });
            }
        }
        //Navigation Bar Code
        bottomNavigationView = findViewById(R.id.detailsBottomNavigationBar);
        bottomNavigationView.getMenu().removeItem(R.id.searchPosts);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()){

                case R.id.go_home:
                    intent = new Intent(DetailsActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;

                case R.id.seller_list:
                    intent = new Intent(DetailsActivity.this, SellerListActivity.class);
                    startActivity(intent);
                    break;

                case R.id.userSettings:
                    intent = new Intent(DetailsActivity.this, EditUserActivity.class);
                    startActivity(intent);
                    break;

                case R.id.add_post:
                    intent = new Intent(DetailsActivity.this, PostAdActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        });

        Button emailBtn = findViewById(R.id.emailUser);
        emailBtn.setOnClickListener(v -> {

            Uri uri = Uri.parse("mailto:" + userEmail)
                    .buildUpon()
                    .appendQueryParameter("subject", titleText.getText() + "Book Ad on Barter Books")
                    .build();

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(Intent.createChooser(emailIntent, "Select your Email app"));
        });
    }




}