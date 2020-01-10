package com.example.rud.fragments.stadisticsFragment.interactor;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rud.R;
import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.adapter.StadisticsAdapterRecyclerView;
import com.example.rud.fragments.stadisticsFragment.presenter.StadisticsPresenter;
import com.example.rud.fragments.stadisticsFragment.repository.StadisticsRepository;
import com.example.rud.fragments.stadisticsFragment.repository.StadisticsRepositoryImpl;
import com.example.rud.model.Evidence;
import com.example.rud.model.Stadistics;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class StadisticsInteractorImpl implements StadisticsInteractor {
    private StadisticsPresenter stadisticsPresenter;
    private StadisticsRepository stadisticsRepository;

    public StadisticsInteractorImpl(StadisticsPresenter stadisticsPresenter){
        this.stadisticsPresenter=stadisticsPresenter;
        stadisticsRepository=new StadisticsRepositoryImpl(stadisticsPresenter);
    }

    @Override
    public void checkDatabase( ArrayList<Stadistics> categorias,RecyclerView estadisticaRecycler,StadisticsAdapterRecyclerView estadisticaAdapterRecyclerView,DatabaseReference databaseReference, String buscar) {
        stadisticsRepository.checkDatabase(categorias,estadisticaRecycler,estadisticaAdapterRecyclerView,databaseReference,buscar);
    }

    @Override
    public void AsignaEstadisticas(ArrayList<Stadistics> categorias,RecyclerView estadisticaRecycler,StadisticsAdapterRecyclerView estadisticaAdapterRecyclerView, int ExcesoFuerza, int NoConvecional, int UsoLetal, int amenaza, int AgresionaDDHH, int Noidentificado, int Nodistingue, int RetencionIlegal, int genero, int protocolo, int herido) {
        estadisticaRecycler.setAdapter(estadisticaAdapterRecyclerView);

        estadisticaRecycler.setAdapter(estadisticaAdapterRecyclerView);

        String urlherido=stadisticsPresenter.getURLForResource(R.drawable.herido);
        String urlexcesodefuerza=stadisticsPresenter.getURLForResource(R.drawable.exceso_de_fuerza);
        String urlagente=stadisticsPresenter.getURLForResource(R.drawable.agentenoidentificado);
        String urlretencion=stadisticsPresenter.getURLForResource(R.drawable.retencionilegal);
        String urlagresionddhh=stadisticsPresenter.getURLForResource(R.drawable.agresion_a_derechos_humanos);
        String urlusoletal=stadisticsPresenter.getURLForResource(R.drawable.usopotencialmenteletal);
        String urlnoconvecional=stadisticsPresenter.getURLForResource(R.drawable.usonoconvencionaldearmas);
        String urlseguimientoIlegal=stadisticsPresenter.getURLForResource(R.drawable.amenzapresionseguimientoilegal);
        String urlProtocolo=stadisticsPresenter.getURLForResource(R.drawable.violaciondeprotocolo);
        String urlGenero=stadisticsPresenter.getURLForResource(R.drawable.genero);

        Stadistics catestadistica1=new Stadistics(urlexcesodefuerza,"Exceso de fuerza",String.valueOf(ExcesoFuerza));
        Stadistics catestadistica2=new Stadistics(urlagente,"Agente no identificado",String.valueOf(Noidentificado));
        Stadistics catestadistica3=new Stadistics(urlretencion,"Retencion ilegal",String.valueOf(RetencionIlegal));
        Stadistics catestadistica4=new Stadistics(urlagresionddhh,"Agresion a derechos humanos",String.valueOf(AgresionaDDHH));
        Stadistics catestadistica5=new Stadistics(urlusoletal,"Uso letal de armamento de letalidad reducida",String.valueOf(UsoLetal));
        Stadistics catestadistica6=new Stadistics(urlnoconvecional,"Armamento no convecional",String.valueOf(NoConvecional));
        Stadistics catestadistica7=new Stadistics(urlseguimientoIlegal,"Ameanza, presion o seguimiento ilegal",String.valueOf(amenaza));
        Stadistics catestadistica8=new Stadistics(urlProtocolo,"Violacion de protocolo",String.valueOf(protocolo));
        Stadistics catestadistica9=new Stadistics(urlGenero,"Genero",String.valueOf(genero));
        Stadistics catestadistica10=new Stadistics(urlherido,"Herido",String.valueOf(herido));

        categorias.clear();

        categorias.add(catestadistica1);
        categorias.add(catestadistica2);
        categorias.add(catestadistica3);
        categorias.add(catestadistica4);
        categorias.add(catestadistica5);
        categorias.add(catestadistica6);
        categorias.add(catestadistica7);
        categorias.add(catestadistica8);
        categorias.add(catestadistica9);
        categorias.add(catestadistica10);
    }
}
