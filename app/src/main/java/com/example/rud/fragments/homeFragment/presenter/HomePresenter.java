package com.example.rud.fragments.homeFragment.presenter;

import android.view.View;

import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.model.Evidence;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface HomePresenter {
    void loadDocuments(final EvidenceAdapterRecyclerView evidenceAdapterRecyclerView, DatabaseReference databaseReference, final ArrayList<Evidence> evidences, final String buscar);
    void showDocumentsSucces();
    void showError(String error);
    void showToolbar(String tittle, boolean upButton, View view);
}

