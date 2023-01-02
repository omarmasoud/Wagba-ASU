package com.example.wagba.Models;

import android.widget.ImageView;

public class MenuItem {
    private String name;
    private float price;
    private int count;
    private ImageView image;
    private String ImageUrl;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageSource) {
        ImageUrl = imageSource;
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



    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }



}
