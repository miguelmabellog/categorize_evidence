package com.example.rud.view;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.rud.R;
import com.example.rud.fragments.Custody_fragment;
import com.example.rud.fragments.homeFragment.view.Home_fragment;
import com.example.rud.fragments.stadisticsFragment.view.Stadistics_fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class Container extends AppCompatActivity  {
    Home_fragment home;
    Custody_fragment custody;
    Stadistics_fragment statistics;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String TAG="ContainerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        firebaseinitialize();

        BottomBar bottomBar=findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.home);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.home:
                        Home_fragment home = new Home_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, home)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.custody:
                        Custody_fragment custody = new Custody_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, custody)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.statistics:
                        Stadistics_fragment statistics = new Stadistics_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, statistics)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                }
            }
        });




    }


    private void firebaseinitialize(){
        firebaseAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    Log.w(TAG,"usuario logueado"+firebaseUser.getEmail());
                }else{
                    Log.w(TAG,"usuario no logueado");
                }
            }
        };
    }
}
