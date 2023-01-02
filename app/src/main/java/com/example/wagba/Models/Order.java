package com.example.wagba.Models;

import com.example.wagba.RemoteAccess.FirebaseAccessor;
import com.google.firebase.database.DatabaseReference;

public class Order {
    String Date;
    String Status;
    public DatabaseReference statusReference;
    public String getDeliveryLocation() {
        return DeliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        DeliveryLocation = deliveryLocation;
    }

    String DeliveryLocation;
    long ItemCount;
    long Price;

    public DatabaseReference getStatusReference() {
        return statusReference;
    }

    public void setStatusReference(DatabaseReference statusReference) {
        this.statusReference = statusReference;
    }

    public Order(){
        ItemCount = 0;
        Date = "";
        Status = "pending";
        Price = 20;
        DeliveryLocation= "3";
//        statusReference= FirebaseAccessor.getInstance().getOrderStatus(this.Date);
    }
    public Order(long itemCount, String date, String status, long price) {
        ItemCount = itemCount;
        Date = date;
        Status = status;
        Price = price;
//        statusReference= FirebaseAccessor.getInstance().getOrderStatus(this.Date);
    }

    public long getItemCount() {
        return ItemCount;
    }

    public void setItemCount(long itemCount) {
        ItemCount = itemCount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }
}
