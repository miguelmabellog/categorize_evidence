package com.example.rud.fragments.homeFragment.repository;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rud.R;
import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.fragments.homeFragment.presenter.HomePresenter;
import com.example.rud.model.Evidence;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeRepositoryImpl extends Fragment implements HomeRepository{
    HomePresenter homePresenter;
    public HomeRepositoryImpl(HomePresenter homePresenter){
        this.homePresenter=homePresenter;
    }

    @Override
    public void loadDocuments(final EvidenceAdapterRecyclerView evidenceAdapterRecyclerView, DatabaseReference databaseReference, final ArrayList<Evidence> evidences, final String buscar) {

        databaseReference.child("Abusos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                evidences.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()) {

                        Evidence evidence = snapshot.getValue(Evidence.class);
                        Log.w(TAG, "IdEvidence: " + evidence.getId());
                        Bundle recuperadatos = getArguments();
                        if (evidence.getId() != null){
                            String clave=buscar;
                            if (clave != null) {
                                //String clave = recuperadatos.getString("envia_clave");

                                //String clave="7-1-2020";
                                if (evidence.getDate().equals(clave) && (evidence.getType().equals("carpeta") == false)) {

                                    evidences.add(evidence);


                                }
                                if (clave.equals("todo")) {
                                    if (evidence.getType().equals("carpeta")) {

                                        Evidence evidenceObject = new Evidence("", evidence.getTime(), evidence.getDate(), evidence.getPlace(), evidence.getCategory(), evidence.getDescription(), evidence.getId(), evidence.getType());
                                        evidences.add(evidenceObject);
                                    }

                                }

                            }else{

                                if(evidence.getType().equals("carpeta")) {
                                    Evidence evidenceObject = new Evidence("", evidence.getTime(), evidence.getDate(), evidence.getPlace(), evidence.getCategory(), evidence.getDescription(), evidence.getId(), evidence.getType());
                                    evidences.add(evidenceObject);
                                }
                            }
                        }

                        homePresenter.showDocumentsSucces();

                    }
                }
                evidenceAdapterRecyclerView.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                homePresenter.showError(String.valueOf(databaseError));
            }
        });
    }
}
