package com.example.barterbooksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.barterbooksapp.utlity.BookPostDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class PostAdActivity extends AppCompatActivity {

    private ArrayList photosUrls = new ArrayList<Uri>();
    private ArrayList imageUrlList = new ArrayList<String>();
    private DatabaseReference budgetRef;
    int SELECT_PICTURE = 200;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);
        budgetRef = FirebaseDatabase.getInstance().getReference().child("testBarterBooks");

//        Set on Click listner calling select image for the image select button



//        save post
//        db.collection("BarterBooksDB")
//                .add(bookPost)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.i("DB", "DocumentSnapshot written with ID: " + documentReference.getId());
//                        uploadImage(it.id)
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.i("DB", "Error adding document", e);
//                    }
//                });

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

    private void uploadImage(String toString) {
//        PB progress Dialog
//        pb.setMessage("Uploading photos")
//        pb.setCancelable(false)
//        pb.show()

//        DatabaseReference storageRef = FirebaseStorage.getInstance().getReference("post_images");

//        int i = 0;
//        while (i < photosUrls.size()) {
//            Uri image = (Uri) photosUrls.get(i);
//            val imageName = storageRef.child(image.lastPathSegment.toString())
//            imagename.putFile(photosUrls[i]).addOnSuccessListener {
//
//                imagename.downloadUrl.addOnSuccessListener {
//                    val url = it.toString()
//                    sendLink(url, toString)
//                }
//
//            }.addOnFailureListener {
//
//            }
//            i++
//        }
//        clearAllFields()

    }

    private void sendLink(String url, String toString) {
        imageUrlList.add(url);

        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

        fireStore.collection("testBarterBooks")
                .document(toString)
                .update("images", imageUrlList)
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
                if (null != selectedImageUri) {
                    // update the preview image in the layout
//                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }


}