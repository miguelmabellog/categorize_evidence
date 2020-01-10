package com.example.rud.login.interactor;

import android.app.Activity;

import com.example.rud.login.presenter.LoginPresenter;
import com.example.rud.login.presenter.LoginPresenterImpl;
import com.example.rud.login.repository.LoginRepository;
import com.example.rud.login.repository.LoginRepositoryImpl;
import com.google.firebase.auth.FirebaseAuth;

public class LoginInteractorImpl implements LoginInteractor{
    private LoginPresenter presenter;
    private LoginRepository repository;
    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter=presenter;
        repository=new LoginRepositoryImpl(presenter);
    }


    @Override
    public void singIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        repository.signIn(username,password,activity,firebaseAuth);
    }
}
