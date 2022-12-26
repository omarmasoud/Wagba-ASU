package com.example.wagba.Models;

import android.widget.ImageView;

public class MenuItem {
    private String name;
    private float rating;
    private float price;
    private int count;
    private ImageView image;
    private String ImageSource;

    public String getImageSource() {
        return ImageSource;
    }

    public void setImageSource(String imageSource) {
        ImageSource = imageSource;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public MenuItem(){}
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }



}
