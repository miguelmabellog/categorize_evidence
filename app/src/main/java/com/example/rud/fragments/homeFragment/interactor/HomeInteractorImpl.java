package com.example.rud.fragments.homeFragment.interactor;

import com.example.rud.adapter.EvidenceAdapterRecyclerView;
import com.example.rud.fragments.homeFragment.presenter.HomePresenter;
import com.example.rud.fragments.homeFragment.repository.HomeRepository;
import com.example.rud.fragments.homeFragment.repository.HomeRepositoryImpl;
import com.example.rud.model.Evidence;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class HomeInteractorImpl implements HomeInteractor{
    private HomePresenter homePresenter;
    private HomeRepository homeRepository;
    public HomeInteractorImpl(HomePresenter homePresenter){
        this.homePresenter=homePresenter;
        homeRepository=new HomeRepositoryImpl(homePresenter);
    }

    @Override
    public void loadDocuments(EvidenceAdapterRecyclerView evidenceAdapterRecyclerView, DatabaseReference databaseReference, ArrayList<Evidence> evidences, String buscar) {
        homeRepository.loadDocuments(evidenceAdapterRecyclerView,databaseReference,evidences,buscar);
    }
}
