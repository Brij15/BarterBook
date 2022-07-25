package com.example.barterbooksapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barterbooksapp.utlity.ForgotPasswordAlert;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 120;
    Button login;
    TextView signUp;
    TextView email;
    TextView password;

    LoginButton facebookSignIn;
    CallbackManager mCallbackManager;
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

        FacebookSdk.sdkInitialize(getApplicationContext());


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageView googleSignIn = findViewById(R.id.googleSignIn);

        facebookSignIn = findViewById(R.id.facebook_login_button);

        mCallbackManager = CallbackManager.Factory.create();
        facebookSignIn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FB", "facebook:onCancel");
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Toast.makeText(LoginPageActivity.this, "Facebook Sign In Error!",
                        Toast.LENGTH_SHORT).show();
                Log.d("FB", "facebook:onError", e);
            }
        });

        signUp.setOnClickListener(view -> startActivity(new Intent(LoginPageActivity.this, RegistrationScreenActivity.class)));


        login.setOnClickListener(view -> signIN());

        googleSignIn.setOnClickListener(view -> signInGoogle());

        //Forget Password
        TextView forgotPassword = findViewById(R.id.forgetPasswordView);
        forgotPassword.setOnClickListener(view -> {
            ForgotPasswordAlert.showAlertDialog(this, mAuth);

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Exception exception = task.getException();
            if (task.isSuccessful()){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("AUTH", "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("AUTH", "Google sign in failed", e);
                }
            }
            else {
                assert exception != null;
                Toast.makeText(LoginPageActivity.this, "Google Sign In Failed!",
                        Toast.LENGTH_SHORT).show();
                Log.w("AUTH", "Google sign in failed" + exception.toString());
            }
        }
        else{
//            Log.i("FB", "requestCode" + requestCode);
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        // Pass the activity result back to the Facebook SDK

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("AUTH", "signInWithCredential:success");
                        Toast.makeText(LoginPageActivity.this, "Google Sign In Success!",
                                Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null){
                            startActivity(new Intent(LoginPageActivity.this, MainActivity.class));
                            finish();
                        }
                    } else {

                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginPageActivity.this, "Google Sign In Failed!",
                                Toast.LENGTH_SHORT).show();
                        Log.w("AUTH", "signInWithCredential:failure" +task.getException().toString() , task.getException());
                        Log.w("AUTH", "signInWithCredential:failure" + task.getResult().toString() );
                    }
                });
    }

    private void signInGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private static boolean isValidEmail(CharSequence target) {
        return (Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void signIN(){
        String emailAddress = (String) String.valueOf(email.getText());
        String userPassword = (String) String.valueOf(password.getText());
        boolean isValid;

        //validations
        if (emailAddress.isEmpty()) {
            Toast.makeText(LoginPageActivity.this, "Username is empty.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.isEmpty()){
            Toast.makeText(LoginPageActivity.this, "Password is empty.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(emailAddress)){
            Toast.makeText(LoginPageActivity.this, "Invalid email address..",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        isValid = true;

        if (isValid){
            mAuth.signInWithEmailAndPassword(emailAddress, userPassword)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null){
                                startActivity(new Intent(LoginPageActivity.this, MainActivity.class));
                                finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginPageActivity.this, "Incorrect Username or Password!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            Toast.makeText(LoginPageActivity.this, "Username Password Empty.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FB", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("FB", "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user != null){
                            startActivity(new Intent(LoginPageActivity.this, MainActivity.class));
                            finish();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("FB", "signInWithCredential:failure", task.getException());
                        Toast.makeText(LoginPageActivity.this, "Authentication with Facebook failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}