package com.formulator.barterbooksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.formulator.barterbooksapp.R;
import com.formulator.barterbooksapp.utlity.UserModel;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditUserActivity extends AppCompatActivity {
    TextView firstName;
    TextView lastName;
    TextView userName;
    TextView mobileNumber;
    TextView address;
    UserModel user ;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        userName = findViewById(R.id.editUserName);
        mobileNumber = findViewById(R.id.editMobileNumber);
        address = findViewById(R.id.editUserAddress);

        db = FirebaseFirestore.getInstance();
        ImageView logoutImageBtn = findViewById(R.id.logoutButton);

        logoutImageBtn.setOnClickListener(view -> {
            LoginManager.getInstance().logOut();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(EditUserActivity.this, LoginPageActivity.class);
            startActivity(intent);
            finish();
        });


        Button cancelBtn = findViewById(R.id.btnCancelUserSave);
        cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EditUserActivity.this, MainActivity.class);
            startActivity(intent);
        });



        cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EditUserActivity.this, MainActivity.class);
            startActivity(intent);
        });

        //
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Use ID as the indicator this to create a new entry
        String userID = user.getUid();
        // Name, email address, and profile photo Url
        String userEmail =  user.getEmail();
        String userDisplayName =  user.getDisplayName();
        userName.setText(userDisplayName);
        getUserDetails(userID);


        Button updateBtn = findViewById(R.id.btnUpdateUser);
        updateBtn.setOnClickListener(view -> {
            UserModel updatedUser = new UserModel(userID, firstName.getText().toString(), lastName.getText().toString(), mobileNumber.getText().toString(), address.getText().toString(), userEmail );

            if(updatedUser != null){
                db.collection("BarterBooksUsers").document(userID)
                        .set(updatedUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditUserActivity.this,"User Updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditUserActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditUserActivity.this,"Error Updating the User", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

    private void getUserDetails(String userID){

        db.collection("BarterBooksUsers").document(userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
//                        Log.d("Firebase", "DocumentSnapshot data: " + document.getData());
                        user = document.toObject(UserModel.class);
                        if (user != null){
                            firstName.setText(user.getFirstName());
                            lastName.setText(user.getLastName());
                            mobileNumber.setText(user.getMobileNumber());
                            address.setText(user.getUserAddress());
                        }

                    } else {
                        Log.i("DB", "Error getting documents: ", task.getException());
                    }
                });
    }
}