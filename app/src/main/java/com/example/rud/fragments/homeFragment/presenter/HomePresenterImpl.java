package com.example.rud.fragments.homeFragment.presenter;

import android.view.View;

import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.fragments.homeFragment.interactor.HomeInteractor;
import com.example.rud.fragments.homeFragment.interactor.HomeInteractorImpl;
import com.example.rud.fragments.homeFragment.view.HomeView;
import com.example.rud.login.view.LoginView;
import com.example.rud.model.Evidence;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class HomePresenterImpl implements HomePresenter {
    private HomeView homeView;
    private HomeInteractor homeInteractor;
    public HomePresenterImpl(HomeView homeView){
        this.homeView=homeView;
        homeInteractor=new HomeInteractorImpl(this);
    }

    @Override
    public void loadDocuments(EvidenceAdapterRecyclerView evidenceAdapterRecyclerView, DatabaseReference databaseReference, ArrayList<Evidence> evidences, String buscar) {
        homeView.disableInpunts();
        homeView.showProgessBarHome();
        homeInteractor.loadDocuments(evidenceAdapterRecyclerView,databaseReference,evidences,buscar);
    }

    @Override
    public void showDocumentsSucces() {
        homeView.enableInputs();
        homeView.hideProgessBarHome();
    }

    @Override
    public void showError(String error) {
        homeView.enableInputs();
        homeView.hideProgessBarHome();
        homeView.showError(error);
    }

    @Override
    public void showToolbar(String tittle, boolean upButton, View view) {
        homeView.showToolbar(tittle,upButton,view);
    }


}
