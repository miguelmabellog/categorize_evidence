package com.example.rud.fragments.stadisticsFragment.presenter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.adapter.StadisticsAdapterRecyclerView;
import com.example.rud.fragments.stadisticsFragment.interactor.StadisticsInteractor;
import com.example.rud.fragments.stadisticsFragment.interactor.StadisticsInteractorImpl;
import com.example.rud.fragments.stadisticsFragment.view.StadisticsView;
import com.example.rud.model.Evidence;
import com.example.rud.model.Stadistics;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class StadisticsPresenterImpl implements StadisticsPresenter{
    private StadisticsView stadisticsView;
    private StadisticsInteractor stadisticsInteractor;

    public StadisticsPresenterImpl(StadisticsView stadisticsView){
        this.stadisticsView=stadisticsView;
        stadisticsInteractor=new StadisticsInteractorImpl(this);
    }

    @Override
    public void checkDatabase( ArrayList<Stadistics> categorias,RecyclerView estadisticaRecycler,StadisticsAdapterRecyclerView estadisticaAdapterRecyclerView,DatabaseReference databaseReference, String buscar) {
        stadisticsView.disableInpunts();
        stadisticsView.showProgressBar();
        stadisticsInteractor.checkDatabase(categorias,estadisticaRecycler,estadisticaAdapterRecyclerView,databaseReference,buscar);
    }

    @Override
    public void checkSuccess() {
        stadisticsView.hideProgressBar();
        stadisticsView.enableInputs();
    }

    @Override
    public void checkerror(String error) {
        stadisticsView.showError(error);
    }

    @Override
    public void AsignaEstadisticas(ArrayList<Stadistics> categorias,RecyclerView estadisticaRecycler, StadisticsAdapterRecyclerView estadisticaAdapterRecyclerView, int ExcesoFuerza, int NoConvecional, int UsoLetal, int amenaza, int AgresionaDDHH, int Noidentificado, int Nodistingue, int RetencionIlegal, int genero, int protocolo, int herido) {
        stadisticsInteractor.AsignaEstadisticas(categorias,estadisticaRecycler,estadisticaAdapterRecyclerView,ExcesoFuerza,NoConvecional,UsoLetal,amenaza,AgresionaDDHH,Noidentificado,Nodistingue,RetencionIlegal,genero,protocolo,herido);
    }

    @Override
    public String getURLForResource(int resourceId) {
        return stadisticsView.getURLForResource(resourceId);
    }
}
