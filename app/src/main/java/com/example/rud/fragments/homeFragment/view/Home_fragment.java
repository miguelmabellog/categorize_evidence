package com.example.rud.fragments.homeFragment.view;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rud.createFile.NewFolderActivity;
import com.example.rud.R;
import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.fragments.homeFragment.presenter.HomePresenter;
import com.example.rud.fragments.homeFragment.presenter.HomePresenterImpl;
import com.example.rud.fragments.homeFragment.repository.HomeRepositoryImpl;
import com.example.rud.model.Evidence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment extends Fragment implements HomeView{

    private HomePresenter homePresenter;
    private static final String TAG="HOMEFRAGMENT";

    private String photoPathTemp;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    private ArrayList<Evidence> evidences;
    private ArrayList<String> fechas;
    private String clave;
    private String toolbartittle;
    ProgressBar progressBarHome;
    FloatingActionButton searchDocument;
    FloatingActionButton showFolders;
    FloatingActionButton addNewFolder;






    public Home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ArrayList<Evidence> evidences=new ArrayList<>();
        final View view=inflater.inflate(R.layout.fragment_home,container,false);

        searchDocument = view.findViewById(R.id.floatSearch);
        showFolders = view.findViewById(R.id.floatShowFolders);
        addNewFolder = view.findViewById(R.id.floatAdd);
        progressBarHome=view.findViewById(R.id.progressbar_home);

        RecyclerView evidenceRecycler= view.findViewById(R.id.fileRecycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        evidenceRecycler.setLayoutManager(linearLayoutManager);

        final EvidenceAdapterRecyclerView evidenceAdapterRecyclerView=new EvidenceAdapterRecyclerView
                (evidences,R.layout.cardview_evidence,getActivity());

        evidenceRecycler.setAdapter(evidenceAdapterRecyclerView);
        homePresenter=new HomePresenterImpl(this);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        Bundle recuperadatos = getArguments();
        if (recuperadatos!=null){
            clave = recuperadatos.getString("envia_clave");
        }


        showDocuments(evidenceAdapterRecyclerView, databaseReference, evidences);
        showToolbar(toolbartittle,false,view);

        searchDocument.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                buscador();
            }
        });

        showFolders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motrartodo();
            }
        });

        addNewFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarcarpeta();
            }
        });

        return view;

    }
    //repository
    private void showDocuments(final EvidenceAdapterRecyclerView evidenceAdapterRecyclerView, DatabaseReference databaseReference, final ArrayList<Evidence> evidences) {
        homePresenter.loadDocuments(evidenceAdapterRecyclerView,databaseReference,evidences,clave);
        if(clave==null || clave=="todo"){
            toolbartittle="RUD-Carpetas";
        }else {
            toolbartittle="RUD-Fecha:"+clave;
        }

    }
    /*private void showDocuments(final EvidenceAdapterRecyclerView evidenceAdapterRecyclerView, DatabaseReference databaseReference, final ArrayList<Evidence> evidences) {


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
                            if (recuperadatos != null) {
                                clave = recuperadatos.getString("envia_clave");
                                if (evidence.getDate().equals(clave) && (evidence.getType().equals("carpeta") == false)) {

                                    evidences.add(evidence);


                                }
                                if (clave.equals("todo")) {
                                    if (evidence.getType().equals("carpeta")) {
                                        String urlPhoto = getURLForResource(R.drawable.carpetalogo);
                                        Evidence evidenceObject = new Evidence(urlPhoto,evidence.getTime(),  evidence.getDate(), evidence.getPlace(), evidence.getCategory(), evidence.getDescription(), evidence.getId(), evidence.getType());
                                        evidences.add(evidenceObject);
                                    }

                                }

                            } else {

                                if (evidence.getType().equals("carpeta")) {
                                    String urlPhoto = getURLForResource(R.drawable.carpetalogo);
                                    Evidence evidenceObject = new Evidence(urlPhoto,evidence.getTime(),  evidence.getDate(), evidence.getPlace(), evidence.getCategory(), evidence.getDescription(), evidence.getId(), evidence.getType());
                                    evidences.add(evidenceObject);


                                }
                            }
                        }



                    }
                }
                evidenceAdapterRecyclerView.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    @Override
    public void agregarcarpeta() {
        Intent i=new Intent(getActivity(), NewFolderActivity.class);
        startActivity(i);
    }

    @Override
    public void motrartodo() {
        clave=("todo");
        Bundle bundle=new Bundle();
        bundle.putString("envia_clave",clave);
        Home_fragment home = new Home_fragment();
        home.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.container, home)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void buscador() {
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                clave=(String.valueOf(dayOfMonth)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year));
                toolbartittle=clave;
                Bundle bundle=new Bundle();
                bundle.putString("envia_clave",clave);
                bundle.putString("envia_titulo",toolbartittle);
                Home_fragment home = new Home_fragment();
                home.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, home)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });
        datePickerDialog.show();
    }

    @Override
    public void showToolbar(String tittle, boolean upButton, View view) {
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
    @Override
    public String getURLForResource (int resourceId) {

        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
    @Override
    public void enableInputs() {
        searchDocument.setEnabled(true);
        addNewFolder.setEnabled(true);
        showFolders.setEnabled(true);
    }
    @Override
    public void disableInpunts(){
        searchDocument.setEnabled(false);
        addNewFolder.setEnabled(false);
        showFolders.setEnabled(false);
    }

    @Override
    public void showProgessBarHome(){
        progressBarHome.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideProgessBarHome(){
        progressBarHome.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), "Hubo un error : "+error, Toast.LENGTH_SHORT).show();
    }

}

    //view
    /*private void agregarcarpeta() {
        Intent i=new Intent(getActivity(), NewFolderActivity.class);
        startActivity(i);
    }
    //view
    private void motrartodo() {
        clave=("todo");
        Bundle bundle=new Bundle();
        bundle.putString("envia_clave",clave);
        Home_fragment home = new Home_fragment();
        home.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.container, home)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();

    }
    //view
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buscador() {
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                clave=(String.valueOf(dayOfMonth)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year));
                Bundle bundle=new Bundle();
                bundle.putString("envia_clave",clave);
                Home_fragment home = new Home_fragment();
                home.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, home)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });
        datePickerDialog.show();

    }


    //view
    public void showToolbar(String tittle,boolean upButton,View view){
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
    //view
    public String getURLForResource (int resourceId) {

        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }*/



