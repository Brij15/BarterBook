package com.example.barterbooksapp;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

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

            password.setError("Password too weak");
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

        if(!validateEmail() | !validateUsername() |!validatePassword()| !validateConfirmPassword()){
            return;
        }
        else {
            startActivity(new Intent(RegistrationScreenActivity.this, MainActivity.class));
            finish();
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