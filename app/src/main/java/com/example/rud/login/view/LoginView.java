package com.example.rud.login.view;

public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showProgressBar();
    void hideProgressBar();
    void loginError(String error);
    void irHome();
}
