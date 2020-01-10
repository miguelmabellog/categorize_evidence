package com.example.rud.model;

public class Stadistics {
    private String pictureE;
    private String categoriaE;
    private String totalE;

    public Stadistics(){

    }

    public Stadistics(String pictureE, String categoriaE, String totalE) {
        this.pictureE = pictureE;
        this.categoriaE = categoriaE;
        this.totalE = totalE;
    }

    public String getPictureE() {
        return pictureE;
    }

    public void setPictureE(String pictureE) {
        this.pictureE = pictureE;
    }

    public String getCategoriaE() {
        return categoriaE;
    }

    public void setCategoriaE(String categoriaE) {
        this.categoriaE = categoriaE;
    }

    public String getTotalE() {
        return totalE;
    }

    public void setTotalE(String totalE) {
        this.totalE = totalE;
    }
}
