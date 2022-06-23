package com.example.barterbooksapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.barterbooksapp.utlity.BookPostDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
    String filterValue;

    private BottomNavigationView bottomNavigationView;
    FirebaseFirestore db;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        db = FirebaseFirestore.getInstance();

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
                filterValue = thisIntent.getStringExtra("postID");
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        ImageButton emailBtn = findViewById(R.id.emailUser);
        ImageButton notifyBtn = findViewById(R.id.notifyUser);

        ImageButton editBtn = findViewById(R.id.editAd);
        ImageButton deleteBtn = findViewById(R.id.deleteAd);

        if (user.getUid() == book.getUserID()){
            emailBtn.setVisibility(View.GONE);
            notifyBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
        }

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

        editBtn.setOnClickListener(v -> {
            Uri uri = Uri.parse("mailto:" + userEmail)
                    .buildUpon()
                    .appendQueryParameter("subject", titleText.getText() + "Book Ad on Barter Books")
                    .build();

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(Intent.createChooser(emailIntent, "Select your Email app"));
        });

        deleteBtn.setOnClickListener(v -> {
            deleteAlert();
        });


    }

    private void deleteAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setCancelable(true);
        builder.setMessage("Do you really want to delete this Ad?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(filterValue != null){
                            db.collection("BarterBooksDB").document(filterValue)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            finish();
                                            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"Error Deleting AD",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            Toast.makeText(getApplicationContext(),"Ad Deleted",
                                    Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Delete Ad");
        alert.show();
    }




}