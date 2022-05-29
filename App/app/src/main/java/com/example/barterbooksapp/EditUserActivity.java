package com.example.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

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

        //
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Use ID as the indicator this to create a new entry
        String userID = user.getUid();
        // Name, email address, and profile photo Url
        String userEmail =  user.getEmail();
        String userDisplayName =  user.getDisplayName();
    }
}