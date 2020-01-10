package com.example.rud.fragments.stadisticsFragment.view;

import android.view.View;

public interface StadisticsView {
    String getURLForResource(int resourceId);
    void motrartodo();
    void buscador();
    void showToolbar(String tittle, boolean upButton, View view);
    void enableInputs();
    void disableInpunts();
    void showProgressBar();
    void hideProgressBar();
    void showError(String error);
}
