package com.example.wagba.RemoteAccess;

import android.util.Log;

import com.example.wagba.Models.Restaurant;
import com.example.wagba.Models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseAccessor {
    private static FirebaseAccessor firebaseAccessor;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAccessor(){
        this.database=FirebaseDatabase.getInstance();
        this.databaseReference=database.getReference("Wagba");
    }
    public static FirebaseAccessor getInstance(){
        if (FirebaseAccessor.firebaseAccessor==null)
        {
            firebaseAccessor=new FirebaseAccessor();
        }
        return FirebaseAccessor.firebaseAccessor;
    }
    public User getUserData(String UID)
    {
        DatabaseReference temp=databaseReference.child("Users").child(UID);
        User newUser=new User(temp.child("email").toString(),temp.child("firstname").toString(),temp.child("lastname").toString());
        return  newUser;
    }
    public DatabaseReference getRestaurantData(String RestaurantName){
        Restaurant restaurant=new Restaurant();
        DatabaseReference temp=databaseReference.child("Restaurants").child(RestaurantName);
        restaurant.setImageUrl(temp.child("image").toString());
        restaurant.setName(temp.child("name").toString());
        Log.d("TAG firebase accessor", temp.child("image").toString());
        return temp.child("image");

    }

}
