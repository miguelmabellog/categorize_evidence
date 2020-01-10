package com.example.rud.fragments.homeFragment.repository;

import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.model.Evidence;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface HomeRepository {
    void loadDocuments(final EvidenceAdapterRecyclerView evidenceAdapterRecyclerView, DatabaseReference databaseReference, final ArrayList<Evidence> evidences, String buscar);
}
