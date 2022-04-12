package com.example.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

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
    }
}