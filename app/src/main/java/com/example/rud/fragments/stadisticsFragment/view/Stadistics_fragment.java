package com.example.rud.fragments.stadisticsFragment.view;


import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.example.rud.R;
import com.example.rud.adapter.StadisticsAdapterRecyclerView;
import com.example.rud.fragments.homeFragment.view.Home_fragment;
import com.example.rud.fragments.stadisticsFragment.presenter.StadisticsPresenter;
import com.example.rud.fragments.stadisticsFragment.presenter.StadisticsPresenterImpl;
import com.example.rud.model.Evidence;
import com.example.rud.model.Stadistics;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Stadistics_fragment extends Fragment implements StadisticsView{
    private static final String TAG="HOMEFRAGMENT";

    StadisticsPresenter stadisticsPresenter;
    FloatingActionButton searchDocumentStadistics;
    FloatingActionButton showStadistics;
    ProgressBar progressBarStadistics;
    private String toolbartittle;
    private DatabaseReference databaseReference;
    private String clave;
    int ExcesoFuerza=0;
    int Noidentificado=0;
    int RetencionIlegal=0;
    int AgresionaDDHH=0;
    int UsoLetal=0;
    int NoConvecional=0;
    int amenaza=0;
    int genero=0;
    int protocolo=0;
    int herido=0;



    final ArrayList<Stadistics> categorias=new ArrayList<>();
    final StadisticsAdapterRecyclerView estadisticaAdapterRecyclerView=new StadisticsAdapterRecyclerView(categorias,R.layout.cardview_stadistics,getActivity());



    public Stadistics_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_statistics,container,false);

        stadisticsPresenter=new StadisticsPresenterImpl(this);



        RecyclerView estadisticaRecycler= view.findViewById(R.id.estadisticaRecycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        estadisticaRecycler.setLayoutManager(linearLayoutManager);



        progressBarStadistics=view.findViewById(R.id.progressbar_stadistics);
        searchDocumentStadistics = view.findViewById(R.id.floatingStadisticsSearch);
        showStadistics = view.findViewById(R.id.floatingStadisticsShow);

        Bundle recuperadatos = getArguments();
        if (recuperadatos!=null){
            clave = recuperadatos.getString("envia_clave");
        }

        stadisticsPresenter.checkDatabase(categorias,estadisticaRecycler,estadisticaAdapterRecyclerView,databaseReference,clave);
        if(clave==null || clave=="todo"){
            toolbartittle="RUD-Total estadisticas";
        }else {
            toolbartittle="RUD-Estadisticas fecha:"+clave;
        }
        showToolbar(toolbartittle,false,view);

        searchDocumentStadistics.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //takePicture();
                buscador();

            }


        });

        showStadistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                motrartodo();
            }
        });



        return view;
    }



    @Override
    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

    @Override
    public void motrartodo() {
        clave=("todo");
        //Toast.makeText(getActivity(), clave, Toast.LENGTH_SHORT).show();

        Bundle bundle=new Bundle();
        bundle.putString("envia_clave",clave);


        Stadistics_fragment stadistics_fragment = new Stadistics_fragment();
        stadistics_fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.container, stadistics_fragment)
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
                Bundle bundle=new Bundle();
                bundle.putString("envia_clave",clave);
                Stadistics_fragment stadistics_fragment = new Stadistics_fragment();
                stadistics_fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, stadistics_fragment)
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
    public void enableInputs() {
        searchDocumentStadistics.setEnabled(true);
        showStadistics.setEnabled(true);
    }

    @Override
    public void disableInpunts(){
        searchDocumentStadistics.setEnabled(false);
        showStadistics.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBarStadistics.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarStadistics.setVisibility(View.GONE);
    }



    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), "Hubo un error : "+error, Toast.LENGTH_SHORT).show();
    }



    //view
    /*private String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
    //view
    private void motrartodo() {
        clave=("todo");
        //Toast.makeText(getActivity(), clave, Toast.LENGTH_SHORT).show();

        Bundle bundle=new Bundle();
        bundle.putString("envia_clave",clave);


       Home_fragment homeFragment2 = new Home_fragment();
        homeFragment2.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.container, homeFragment2)
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
                clave=(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                //Toast.makeText(getActivity(), clave, Toast.LENGTH_SHORT).show();

                Bundle bundle=new Bundle();
                bundle.putString("envia_clave",clave);


                Home_fragment homeFragment2 = new Home_fragment();
                homeFragment2.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, homeFragment2)
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
    }*/
}
