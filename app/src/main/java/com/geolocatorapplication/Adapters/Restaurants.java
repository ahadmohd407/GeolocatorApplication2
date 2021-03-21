package com.geolocatorapplication.Adapters;

public class Restaurants {
    private String res_name;
    private String ratings;

    public Restaurants(String res_name, String ratings) {
        this.res_name = res_name;
        this.ratings = ratings;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }
}
