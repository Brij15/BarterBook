package com.formulator.barterbooksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.formulator.barterbooksapp.R;
import com.formulator.barterbooksapp.utlity.BookPostDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;

public class PostAdActivity extends AppCompatActivity {

    private ArrayList photosUrls = new ArrayList<Uri>();
    private ArrayList imageUrlList = new ArrayList<String>();
    private DatabaseReference budgetRef;
    int SELECT_PICTURE = 200;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Spinner bookCat;
    EditText bookName;
    EditText bookAuthor;
    EditText bookDescription;
    Spinner bookCondition;
    EditText bookPrice;
    CheckBox barter;
    EditText contactDetails;
    ImageButton selectImage;
    Spinner bookLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);
        budgetRef = FirebaseDatabase.getInstance().getReference().child("testBarterBooks");

        bookCat = findViewById(R.id.bookType);
        bookName = findViewById(R.id.txtBookName);
        bookAuthor = findViewById(R.id.txtBookAuthor);
        bookDescription = findViewById(R.id.txtDescription);
        bookCondition = findViewById(R.id.bookCondition);
        bookPrice = findViewById(R.id.txtBookPrice);
        barter = findViewById(R.id.chkBarter);
        contactDetails = findViewById(R.id.txtContactDetails);
        selectImage = findViewById(R.id.selectImage);
        bookLocation = findViewById(R.id.bookLocation);

//        Set on Click listener calling select image for the image select button
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


        Button saveButton = findViewById(R.id.btnPostAd);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Do Save here
                saveBook();
            }
        });

        Button cancelButton = findViewById(R.id.btnCancelAD);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostAdActivity.this, MainActivity.class));
            }
        });

    }
    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK ,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        String mimeType[] = { "image/jpeg", "image/png", "image/jpg"};
//        ArrayList<String> mimeType = new ArrayList<String>();
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivityForResult(intent, SELECT_PICTURE);
    }

    private void saveBook(){

        String strBookCategory = bookCat.getSelectedItem().toString();
        String strBookCondition = bookCondition.getSelectedItem().toString();
        String strBookName = bookName.getText().toString();
        String strBookAuthor = bookAuthor.getText().toString();
        String strDescription = bookDescription.getText().toString();
        String strPrice = bookPrice.getText().toString();
        Boolean isBarter = barter.isChecked();
        String strContactDetails = contactDetails.getText().toString();
        String strBookLocation = bookLocation.getSelectedItem().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        BookPostDataModel book = new BookPostDataModel();
        book.setTitle(strBookName);
        book.setAuthor(strBookAuthor);
        book.setCategory(strBookCategory);
        book.setCondition(strBookCondition);
        book.setPrice(Double.valueOf(strPrice));
        book.setTimePosted(new Date());
        book.setBarter(isBarter);
        book.setDetails(strDescription);
        book.setLocation(strBookLocation);
        book.setUserDetails(strContactDetails);
        if (user != null) {
            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project.
            book.setUserID(user.getUid());
            // Name, email address, and profile photo Url
            book.setUserEmail(user.getEmail());
            book.setUserName(user.getDisplayName());
        }

        if (validateInputs(book)){
            if (book != null){
                db.collection("BarterBooksDB")
                        .add(book)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.i("DB", "DocumentSnapshot written with ID: " + documentReference.getId());
                                uploadImage(documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("DB", "Error adding document", e);
                            }
                        });
            }
        }
        else {
            Toast.makeText(this,"Fill in the details", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInputs(BookPostDataModel book){
        if (book.getCategory().equals("Select Book Category")) {
            Toast.makeText(this,"Select a Category", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (book.getCondition().equals("Select Your Books Condition")){
            Toast.makeText(this,"Select a Condition", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (book.getLocation().equals("Select Your Location")){
            Toast.makeText(this,"Select a Location", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(book.getTitle().isEmpty()){
            bookName.setError("Book Name is Required");
            return false;
        }
        if (book.getAuthor().isEmpty()){bookAuthor.setError("Author is Required"); return false;}
        if (book.getPrice().isNaN()) {
            bookPrice.setError("Price is required");
            return false;
        }
        if ((book.getPrice() < 0) || (book.getPrice() > 999)){
            bookPrice.setError("Price should be between 0 and 999 CAD");
            return false;
        }
        return true;
    }


    private void uploadImage(String documentRefID) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("post_images");

        int i = 0;
        while (i < photosUrls.size()) {
            Uri image = (Uri) photosUrls.get(i);
            StorageReference imageName = storageRef.child(image.getLastPathSegment().toString());
            UploadTask uploadTask = imageName.putFile(image);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                //downloadable uri
                                Uri path = task.getResult();
                                sendLink(path.toString(), documentRefID);
                            }
                        }
                    });
                }
            });
            i++;
        }
        Toast.makeText(this,"New Book Post Added", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PostAdActivity.this, MainActivity.class));
    }

    private void sendLink(String url, String documentID) {
        imageUrlList.add(url);

        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

        fireStore.collection("BarterBooksDB")
                .document(documentID)
                .update("imagesList", imageUrlList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("DB", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("DB", "Error updating document", e);
                    }
                });
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                photosUrls.add(selectedImageUri);
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    selectImage.setImageURI(selectedImageUri);
//                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}