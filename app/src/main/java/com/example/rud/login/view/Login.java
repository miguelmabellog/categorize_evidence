package com.example.rud.login.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rud.view.Container;
import com.example.rud.R;
import com.example.rud.login.presenter.LoginPresenter;
import com.example.rud.login.presenter.LoginPresenterImpl;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements LoginView{
    private TextInputEditText usuario,passwordsign;
    private Button loginButton;
    private ProgressBar progressBar;
    private LoginPresenter presenter;
    private TextView crearCuenta;

    private static final String TAG = "LoginRepositoryImpl";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    Log.w(TAG,"usuario logueado"+firebaseUser.getEmail());
                    irHome();
                }else{
                    Log.w(TAG,"usuario no logueado");
                }
            }
        };


        usuario=findViewById(R.id.usersign);
        passwordsign=findViewById(R.id.passwordsign);
        loginButton=findViewById(R.id.loginbutton);
        progressBar=findViewById(R.id.progressid);

        hideProgressBar();

        presenter=new LoginPresenterImpl(this);




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn(usuario.getText().toString(), passwordsign.getText().toString());

            }
        });
    }

    @Override
    public void enableInputs() {
        usuario.setEnabled(true);
        passwordsign.setEnabled(true);
        loginButton.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        usuario.setEnabled(false);
        passwordsign.setEnabled(false);
        loginButton.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this, "Ocurrio un error"+error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void irHome() {
        Intent intent=new Intent(this, Container.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);


    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    private void signIn(String usurname, String password) {
        presenter.signIn(usurname,password,this, firebaseAuth);
    }
}
