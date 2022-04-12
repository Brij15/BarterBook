package com.example.barterbooksapp.utlity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.barterbooksapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DBUtilities {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void AddPost(BookPostDataModel bookPostDataModel){
        //Dummy Images
        bookPostDataModel.setImagesList(Arrays.asList(R.drawable.default_book,R.drawable.default_book,R.drawable.default_book));
        if (bookPostDataModel != null){
            db.collection("BarterBooksDB")
                    .add(bookPostDataModel)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.i("DB", "DocumentSnapshot written with ID: " + documentReference.getId());
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

    public BookPostDataModel getPost(String ID){
        final BookPostDataModel[] newPost = {null};
        DocumentReference docRef = db.collection("BarterBooksDB").document(ID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                BookPostDataModel post = documentSnapshot.toObject(BookPostDataModel.class);
                newPost[0] = post;
            }
        });
        return newPost[0];
    }



    public Map<String, BookPostDataModel> GetAllPosts(){
        final Map<String,BookPostDataModel> newPosts = new HashMap<>();
        db.collection("BarterBooksDB")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("DB", document.getId() + " => " + document.getData());

                                BookPostDataModel post = document.toObject(BookPostDataModel.class);
                                newPosts.put(document.getId(), post);
                            }
                        } else {
                            Log.i("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return newPosts;
    }

    public void updatePost(BookPostDataModel post, String ID){
//        Not working
        DocumentReference postRef = db.collection("BarterBooksDB").document(ID);

        // Set the "isCapital" field of the city 'DC'
        postRef
                .update("author", post.getAuthor())
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

    public void deletePost(String id){
        db.collection("BarterBooksDB").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("DB", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DB", "Error deleting document", e);
                    }
                });
    }

    public Map<String, BookPostDataModel> GetByCategory(String category){
        final Map<String,BookPostDataModel> newPosts = new HashMap<>();
        db.collection("BarterBooksDB")
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB", document.getId() + " => " + document.getData());
                                BookPostDataModel post = document.toObject(BookPostDataModel.class);
                                newPosts.put(document.getId(), post);
                            }
                        } else {
                            Log.d("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return newPosts;
    }

    public Map<String, BookPostDataModel> GetByLocation(String location){
        final Map<String,BookPostDataModel> newPosts = new HashMap<>();
        db.collection("BarterBooksDB")
                .whereEqualTo("location", location)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB", document.getId() + " => " + document.getData());
                                BookPostDataModel post = document.toObject(BookPostDataModel.class);
                                newPosts.put(document.getId(), post);
                            }
                        } else {
                            Log.d("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return newPosts;
    }

    public Map<String, BookPostDataModel> filterByLocCat(String location, String category){
        final Map<String,BookPostDataModel> newPosts = new HashMap<>();
        db.collection("BarterBooksDB")
                .whereEqualTo("location", location)
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB", document.getId() + " => " + document.getData());
                                BookPostDataModel post = document.toObject(BookPostDataModel.class);
                                newPosts.put(document.getId(), post);
                            }
                        } else {
                            Log.d("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return newPosts;
    }

    public Map<String, BookPostDataModel> Search(String location, String category){
        final Map<String,BookPostDataModel> newPosts = new HashMap<>();
//        TODO- This is just helper code
        db.collection("BarterBooksDB")
                .whereEqualTo("location", location)
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB", document.getId() + " => " + document.getData());
                                BookPostDataModel post = document.toObject(BookPostDataModel.class);
                                newPosts.put(document.getId(), post);
                            }
                        } else {
                            Log.d("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return newPosts;
    }




}
