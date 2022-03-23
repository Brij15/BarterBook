package com.example.barterbooksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button login;
    TextView signUp;
    TextView email;
    TextView password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signUp = findViewById(R.id.textViewSignup);
        login = findViewById(R.id.buttonLogin);

        email = findViewById(R.id.userEmailLogin);
        password = findViewById(R.id.userPasswordLogin);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
//        If the user is not logged out they will be directed to home
        if(currentUser != null){
            startActivity(new Intent(LoginPageActivity.this, MainActivity.class));
            finish();
        }


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPageActivity.this, RegistrationScreenActivity.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIN();
            }
        });


    }

    public static boolean isValidEmail(CharSequence target) {
        return (Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean signIN(){
        String emailAddress = (String) String.valueOf(email.getText());
        String userPassword = (String) String.valueOf(password.getText());
        boolean isValid = false;

        //validations
        if (emailAddress.isEmpty()) {
            Toast.makeText(LoginPageActivity.this, "Username is empty.",
                    Toast.LENGTH_SHORT).show();
            return isValid;
        }

        if (userPassword.isEmpty()){
            Toast.makeText(LoginPageActivity.this, "Password is empty.",
                    Toast.LENGTH_SHORT).show();
            return isValid;
        }

        if (!isValidEmail(emailAddress)){
            Toast.makeText(LoginPageActivity.this, "Invalid email address..",
                    Toast.LENGTH_SHORT).show();
            return isValid;
        }

        isValid = true;



        if (isValid){
            mAuth.signInWithEmailAndPassword(emailAddress, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                if(user != null){
                                    startActivity(new Intent(LoginPageActivity.this, MainActivity.class));
                                    finish();
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginPageActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(LoginPageActivity.this, "Username Password Empty.",
                    Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }
}