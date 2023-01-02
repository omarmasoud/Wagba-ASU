package com.example.wagba.Models;

import android.widget.ImageView;

public class Restaurant {
    private String name;
    private float rating;
    private String location;
    ImageView image;
    String ImageUrl;
    
    public ImageView getImage() {
        return image;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Restaurant(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



}
