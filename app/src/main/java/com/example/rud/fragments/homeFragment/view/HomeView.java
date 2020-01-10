package com.example.rud.fragments.homeFragment.view;

import android.view.View;

import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.model.Evidence;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface HomeView {
     void agregarcarpeta();
     void motrartodo();
     void buscador();
     void showToolbar(String tittle, boolean upButton, View view);
     String getURLForResource (int resourceId);
     void enableInputs();
     void disableInpunts();
     void showProgessBarHome();
     void hideProgessBarHome();
     void showError(String error);

}
