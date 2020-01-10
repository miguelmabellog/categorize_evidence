package com.example.rud.fragments.stadisticsFragment.repository;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.adapter.StadisticsAdapterRecyclerView;
import com.example.rud.fragments.stadisticsFragment.presenter.StadisticsPresenter;
import com.example.rud.model.Evidence;
import com.example.rud.model.Stadistics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StadisticsRepositoryImpl implements StadisticsRepository {
    StadisticsPresenter stadisticsPresenter;
    int ExcesoFuerza=0;
    int NoConvecional=0;
    int UsoLetal=0;
    int amenaza=0;
    int AgresionaDDHH=0;
    int Noidentificado=0;
    int RetencionIlegal=0;
    int genero=0;
    int protocolo=0;
    int herido=0;
    int Nodistingue=0;


    public StadisticsRepositoryImpl(StadisticsPresenter stadisticsPresenter){
        this.stadisticsPresenter=stadisticsPresenter;
    }

    @Override
    public void checkDatabase(final ArrayList<Stadistics> categorias, final RecyclerView estadisticaRecycler, final StadisticsAdapterRecyclerView estadisticaAdapterRecyclerView, DatabaseReference databaseReference, final String buscar) {




        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Abusos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ExcesoFuerza=0;
                NoConvecional=0;
                UsoLetal=0;
                amenaza=0;
                AgresionaDDHH=0;
                Noidentificado=0;
                RetencionIlegal=0;
                genero=0;
                protocolo=0;
                herido=0;
                Nodistingue=0;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Evidence evidencia = snapshot.getValue(Evidence.class);
                        if (buscar==null||buscar.equals("todo")){
                            if (evidencia.getCategory()!= null){

                                if (evidencia.getCategory().equals("Exceso de fuerza")) {
                                    ExcesoFuerza = ExcesoFuerza + 1;
                                }
                                if (evidencia.getCategory().equals("Uso de armamento no convencional")) {

                                    NoConvecional = NoConvecional + 1;
                                }
                                if (evidencia.getCategory().equals("Uso potencialmente letal de armas de letalidad reducida")) {

                                    UsoLetal = UsoLetal + 1;
                                }
                                if (evidencia.getCategory().equals("Amenazas,presiones o seguimientos ilegales")) {

                                    amenaza=amenaza+1;


                                }
                                if (evidencia.getCategory().equals("Amenazas o agresion a defensores o prensa")) {

                                    AgresionaDDHH=AgresionaDDHH+1;
                                }
                                if (evidencia.getCategory().equals("Agente sin identificación")) {

                                    Noidentificado=Noidentificado+1;
                                }
                                if (evidencia.getCategory().equals("Incapacidad de distinguir manifestantes de poblacion civil")) {
                                    Nodistingue=Nodistingue+1;

                                }
                                if (evidencia.getCategory().equals("Retención y conduccion arbitraria")) {


                                    RetencionIlegal=RetencionIlegal+1;

                                }

                                if (evidencia.getCategory().equals("Genero")) {

                                    genero=genero+1;
                                }
                                if (evidencia.getCategory().equals("Violacion de protocolo de accion y verificacion DDHH")) {

                                    protocolo=protocolo+1;
                                }
                                if (evidencia.getCategory().equals("Herido")) {

                                    herido=herido+1;
                                }



                            }
                        }
                        else{
                            if (evidencia.getDate().equals(buscar)){
                                if (evidencia.getCategory().equals("Exceso de fuerza")) {
                                    ExcesoFuerza = ExcesoFuerza + 1;
                                }
                                if (evidencia.getCategory().equals("Uso de armamento no convencional")) {

                                    NoConvecional = NoConvecional + 1;
                                }
                                if (evidencia.getCategory().equals("Uso potencialmente letal de armas de letalidad reducida")) {

                                    UsoLetal = UsoLetal + 1;
                                }
                                if (evidencia.getCategory().equals("Amenazas,presiones o seguimientos ilegales")) {

                                    amenaza=amenaza+1;


                                }
                                if (evidencia.getCategory().equals("Amenazas o agresion a defensores o prensa")) {

                                    AgresionaDDHH=AgresionaDDHH+1;
                                }
                                if (evidencia.getCategory().equals("Agente sin identificación")) {

                                    Noidentificado=Noidentificado+1;
                                }
                                if (evidencia.getCategory().equals("Incapacidad de distinguir manifestantes de poblacion civil")) {
                                    Nodistingue=Nodistingue+1;

                                }
                                if (evidencia.getCategory().equals("Retención y conduccion arbitraria")) {


                                    RetencionIlegal=RetencionIlegal+1;

                                }

                                if (evidencia.getCategory().equals("Genero")) {

                                    genero=genero+1;
                                }
                                if (evidencia.getCategory().equals("Violacion de protocolo de accion y verificacion DDHH")) {

                                    protocolo=protocolo+1;
                                }
                                if (evidencia.getCategory().equals("Herido")) {

                                    herido=herido+1;
                                }

                            }
                        }






                    }

                }

                //estadisticaAdapterRecyclerView.notifyDataSetChanged();
                stadisticsPresenter.AsignaEstadisticas(categorias,estadisticaRecycler,estadisticaAdapterRecyclerView,ExcesoFuerza,NoConvecional,UsoLetal,amenaza,AgresionaDDHH,Noidentificado,Nodistingue,RetencionIlegal,genero,protocolo,herido);
                stadisticsPresenter.checkSuccess();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                stadisticsPresenter.checkerror(String.valueOf(databaseError));
            }


        });
    }
}
