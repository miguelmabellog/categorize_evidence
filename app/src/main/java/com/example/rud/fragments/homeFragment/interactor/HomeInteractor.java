package com.example.rud.fragments.homeFragment.interactor;

import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.model.Evidence;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface HomeInteractor {
    void loadDocuments(final EvidenceAdapterRecyclerView evidenceAdapterRecyclerView, DatabaseReference databaseReference, final ArrayList<Evidence> evidences,String buscar);
}
