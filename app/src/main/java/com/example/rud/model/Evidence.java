package com.example.rud.model;

public class Evidence {
    private String referenceEvidence;
    private String time;
    private String date;
    private String place;
    private String category;
    private String description;
    private String id;
    private String type;

    public Evidence(){

    }

    public Evidence(String referenceEvidence, String time, String date, String place, String category, String description, String id, String type) {
        this.referenceEvidence = referenceEvidence;
        this.time = time;
        this.date = date;
        this.place = place;
        this.category = category;
        this.description = description;
        this.id = id;
        this.type = type;
    }

    public String getReferenceEvidence() {
        return referenceEvidence;
    }

    public void setReferenceEvidence(String referenceEvidence) {
        this.referenceEvidence = referenceEvidence;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
