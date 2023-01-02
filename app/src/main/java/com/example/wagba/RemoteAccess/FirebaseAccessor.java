package com.example.wagba.RemoteAccess;

import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wagba.Models.Cart;
import com.example.wagba.Models.MenuItem;
import com.example.wagba.Models.Order;
import com.example.wagba.Models.Restaurant;
import com.example.wagba.Models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FirebaseAccessor {
    private static FirebaseAccessor firebaseAccessor;
    private FirebaseAuth authenticator;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Date date;
    private SimpleDateFormat formatter;
    private FirebaseAccessor(){
        this.database=FirebaseDatabase.getInstance();
        this.databaseReference=database.getReference("Wagba");
        this.authenticator=FirebaseAuth.getInstance();

        this.formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
//                    menuItem.setRating(Float.parseFloat(dataSnapshot.child("rate").getValue(String.class)));
                    menuItem.setPrice(Integer.parseInt(dataSnapshot.child("price").getValue(String.class)));
                    menuItem.setImageUrl(dataSnapshot.child("image").getValue(String.class));
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
    public DatabaseReference getMenuOf(String RestaurantName){
        DatabaseReference temp=databaseReference.child("Restaurants").child(RestaurantName).child("Dishes");
        return temp;
    }
    public  void addOrder(){
//        this.date= Calendar.getInstance().getTime();
//        String strdate=formatter.format(this.date);
        Cart.getCart().ExportCart();
        Order order=Cart.getCart().getCurrentorder();
//        order.setDeliveryLocation("3");
        String orderdate=order.getDate();
        this.databaseReference.child("Users").child(authenticator.getUid()).child("orders").child(orderdate).setValue(Cart.getCart().getItems());
        this.databaseReference.child("Users").child(authenticator.getUid()).child("orders").child(orderdate).child("orderdata").setValue(order);


    }
    public DatabaseReference getOrders()
    {
        DatabaseReference temp=this.databaseReference.child("Users").child(authenticator.getUid()).child("orders");
//        String Date,Status;
//        int Itemcount;
//        float price;
        return temp;

    }
    public DatabaseReference getOrderStatus(String id){
        DatabaseReference temp=this.databaseReference.child("Users")
                .child(authenticator.getUid())
                .child("orders")
                .child(id)
                .child("orderdata")
                .child("status");
        return temp;
    }

}
