package com.example.barterbooksapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.barterbooksapp.utlity.BookPostDataModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    BookPostDataModel book;

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
                        book = documentSnapshot.toObject(BookPostDataModel.class);
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

        ImageButton emailBtn = findViewById(R.id.emailUser);
        ImageButton notifyBtn = findViewById(R.id.notifyUser);
        emailBtn.setOnClickListener(v -> {
            Uri uri = Uri.parse("mailto:" + userEmail)
                    .buildUpon()
                    .appendQueryParameter("subject", titleText.getText() + "Book Ad on Barter Books")
                    .build();

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(Intent.createChooser(emailIntent, "Select your Email app"));
        });

        notifyBtn.setOnClickListener(v -> {
            // The topic name can be optionally prefixed with "/topics/".
            String topic = book.getUserID();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();

                // The user's ID, unique to the Firebase project.
                book.setUserID(user.getUid());
                // Name, email address, and profile photo Url
                book.setUserEmail(user.getEmail());
                book.setUserName(user.getDisplayName());

                // See documentation on defining a message payload.
//                Message message = Message.builder()
//                        .putData("Title", "Your Post " + titleText.getText().toString())
//                        .putData("Other", "Posted on " + postDetailText.getText().toString() + " Got " + user.getDisplayName()+ "interested!")
//                        .putData("Email",  user.getEmail())
//                        .setTopic(topic)
//                        .build();

//                String response = FirebaseMessaging.getInstance().send(message);
//                System.out.println("Successfully sent message: " + response);
                Toast.makeText(this,"Successfully sent message", Toast.LENGTH_SHORT).show();
            }



        });
    }




}