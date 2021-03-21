package com.geolocatorapplication.Adapters;

public class Reviews {
    private String res_name;
    private String reviews;

    public Reviews(String res_name, String reviews) {
        this.res_name = res_name;
        this.reviews = reviews;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getreviews() {
        return reviews;
    }

    public void setRatings(String reviews) {
        this.reviews = reviews;
    }
}