package com.example.rud.fragments.stadisticsFragment.interactor;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.adapter.StadisticsAdapterRecyclerView;
import com.example.rud.model.Evidence;
import com.example.rud.model.Stadistics;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface StadisticsInteractor {
    void checkDatabase(ArrayList<Stadistics> categorias,RecyclerView estadisticaRecycler,StadisticsAdapterRecyclerView estadisticaAdapterRecyclerView, DatabaseReference databaseReference, String buscar);
    void AsignaEstadisticas(ArrayList<Stadistics> categorias,RecyclerView estadisticaRecycler, StadisticsAdapterRecyclerView estadisticaAdapterRecyclerView, int ExcesoFuerza, int NoConvecional, int UsoLetal, int amenaza, int AgresionaDDHH, int Noidentificado, int Nodistingue, int RetencionIlegal, int genero, int protocolo, int herido);
}
