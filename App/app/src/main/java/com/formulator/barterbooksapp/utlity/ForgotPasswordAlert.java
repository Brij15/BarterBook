package com.formulator.barterbooksapp.utlity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.formulator.barterbooksapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordAlert {
    public static void showAlertDialog(Context context, FirebaseAuth mAuth){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View myView = inflater.inflate(R.layout.search_input, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(true);
        final EditText searchTextView =  myView.findViewById(R.id.search_title);
        final Button cancel = myView.findViewById(R.id.cancel);
        final Button submit = myView.findViewById(R.id.search);
        final TextView header = myView.findViewById(R.id.alertHeader);

        searchTextView.setHint("Your Email Here......");
        header.setText("Forgot your Password?");
        submit.setText("Submit");

        submit.setOnClickListener(view -> {
            String userEmail = searchTextView.getText().toString();

            if (TextUtils.isEmpty(userEmail)){
                searchTextView.setError("Email is Empty");
                return;
            }
            String replacedStr = userEmail.replaceAll("\\s", "");
            if(replacedStr.isEmpty()){
                searchTextView.setError("Email is Empty");
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                searchTextView.setError("Incorrect Email");
                return;
            }

            else{
                Toast.makeText(context, "Password Reset Email Sent", Toast.LENGTH_SHORT).show();
                mAuth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(task -> {
//                                Maybe do a alert Message here
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Reset Email Sent!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            dialog.dismiss();
        });

        cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}
