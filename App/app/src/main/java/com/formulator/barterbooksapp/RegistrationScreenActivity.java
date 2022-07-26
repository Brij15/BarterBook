package com.formulator.barterbooksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.formulator.barterbooksapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegistrationScreenActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    //"(?=.*[a-zA-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{4,}" +
                    "$");


    EditText userName;
    EditText emailAddress;
    EditText password;
    EditText confirmPassword;
    Button register;
    String passwordInput;
    TextView haveAccount;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

         userName = (EditText) findViewById(R.id.userName);
         emailAddress = (EditText) findViewById(R.id.emailAddress);
         password = (EditText) findViewById(R.id.password);
         confirmPassword = (EditText) findViewById(R.id.confirmPassword);
         register = (Button) findViewById(R.id.register);

         haveAccount = (TextView) findViewById(R.id.textViewHaveAccount);

         haveAccount.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(RegistrationScreenActivity.this, LoginPageActivity.class));
                 finish();
             }
         });

    }

    public boolean validateEmail(){

        String emailInput = emailAddress.getText().toString().trim();

        if(emailInput.isEmpty()){
             emailAddress.setError("Field can't be empty");
             return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            emailAddress.setError("please enter a valid Email Address");
            return false;
        }

          else{
            emailAddress.setError(null);
            return true;
        }

    }

    public boolean validateUsername(){

        String usernameInput  = userName.getText().toString().trim();

        if(usernameInput.isEmpty()){
            userName.setError("Field can't be empty");
            return false;
        }else{
            userName .setError(null);
            return true;
        }

    }


    public boolean validatePassword(){

        passwordInput = password.getText().toString().trim();

        if(passwordInput.isEmpty()){
            password.setError("Field can't be empty");
            return false;
        } else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()){

            password.setError("Password must have atleast 9 characters and include atleast one lowercase letter, one uppercase letter and one special character");
            return false;
        }

          else{
            password .setError(null);
            return true;
        }

    }

    public boolean validateConfirmPassword(){

        String confirmPasswordInput = confirmPassword.getText().toString().trim();

        if(confirmPasswordInput.equals(passwordInput)){

             confirmPassword.setError(null);

            return true;

        }else{
            confirmPassword.setError("password do not match");
            return false;
        }

    }





    public void signUp(View view) {

        Log.i("user details", String.valueOf(userName.getText()));
        Log.i("user details", String.valueOf(emailAddress.getText()));
        Log.i("user details", String.valueOf(password.getText()));
        Log.i("user details", String.valueOf(confirmPassword.getText()));


        String email = (String) String.valueOf(emailAddress.getText());
        String userPassword = (String) String.valueOf(password.getText());

        if(!validateEmail() | !validateUsername() |!validatePassword()| !validateConfirmPassword()){
            return;
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(RegistrationScreenActivity.this, MainActivity.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegistrationScreenActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
        }
    }

    public void  behanceSignUp(View view){
        Toast.makeText(this, "Sign Up Using Behance", Toast.LENGTH_SHORT).show();
    }

    public void  googleSignUp(View view){
        Toast.makeText(this, "Sign Up Using Google", Toast.LENGTH_SHORT).show();
    }

    public void  facebookSignUp(View view){
        Toast.makeText(this, "Sign Up Using Facebook", Toast.LENGTH_SHORT).show();
    }

    public void  twitterSignUp(View view){
        Toast.makeText(this, "Sign Up Using Twitter", Toast.LENGTH_SHORT).show();
    }

    public void  linkedInSignUp(View view){
        Toast.makeText(this, "Sign Up Using LinkedIn", Toast.LENGTH_SHORT).show();
    }



}