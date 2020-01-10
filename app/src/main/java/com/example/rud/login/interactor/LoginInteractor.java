package com.example.rud.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public interface LoginInteractor {
    void singIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth);
}
