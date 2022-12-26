package com.example.wagba.RemoteAccess;

import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wagba.Models.MenuItem;
import com.example.wagba.Models.Restaurant;
import com.example.wagba.Models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        User newUser=new User(temp.child("email").toString(),temp.child("firstName").toString(),temp.child("lastName").toString(),UID);
        return  newUser;
    }
    public DatabaseReference getRestaurantImage(String RestaurantName){
        DatabaseReference temp=databaseReference.child("Restaurants").child(RestaurantName);

        return temp.child("image");

    }
    public ArrayList<MenuItem> getDishes(String RestaurantName, Context context) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        DatabaseReference items = this.databaseReference.child("Restaurants").child(RestaurantName).child("Dishes");
        items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MenuItem menuItem = new MenuItem();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    menuItem.setName(dataSnapshot.child("name").getValue(String.class));
                    menuItem.setRating(Float.parseFloat(dataSnapshot.child("rate").getValue(String.class)));
                    menuItem.setPrice(Integer.parseInt(dataSnapshot.child("price").getValue(String.class)));
                    menuItem.setImageSource(dataSnapshot.child("image").getValue(String.class));
                    menuItems.add(menuItem);
                }
                Toast.makeText(context, "loaded " + RestaurantName + " dishes successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Failed to load " + RestaurantName + " dishes", Toast.LENGTH_SHORT).show();

            }
        });

    return menuItems;
    }
    public DatabaseReference getRestaurantData(String RestaurantName){
        DatabaseReference temp=databaseReference.child("Restaurants").child(RestaurantName);
        return temp;
    }
    public DatabaseReference getRestaurants(){
        return databaseReference.child("Restaurants");
    }
    public void addUser(User user){
        this.databaseReference.child("Users").child(user.getUID()).setValue(user);

    }

}
