package com.example.rud.context;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RudAplication extends Application {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private FirebaseStorage firebaseStorage;
    @Override
    public void onCreate() {
        super.onCreate();

        firebaseAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    Log.w("RudAplication","usuario logueado"+firebaseUser.getEmail());

                }else{
                    Log.w("RudAplication","usuario no logueado");
                }
            }
        };

        firebaseStorage=FirebaseStorage.getInstance();



    }

    public StorageReference getStorageReference(){
        return firebaseStorage.getReference();
    }

}

