package com.example.rud.fragments.stadisticsFragment.repository;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.adapter.StadisticsAdapterRecyclerView;
import com.example.rud.model.Evidence;
import com.example.rud.model.Stadistics;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface StadisticsRepository {
    void checkDatabase(ArrayList<Stadistics> categorias,RecyclerView estadisticaRecycler, StadisticsAdapterRecyclerView estadisticaAdapterRecyclerView, DatabaseReference databaseReference, String buscar);
}
