package com.example.wagba.Recyclers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.wagba.R;
import com.example.wagba.RemoteAccess.FirebaseAccessor;
import com.example.wagba.RestaurantRelatedScreens.MenuScreen;

import java.util.ArrayList;

import com.example.wagba.Models.Restaurant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RestaurantsRecyclerAdapter extends RecyclerView.Adapter<RestaurantsRecyclerAdapter.RestaurantsRecyclerViewHolder> {
    Context context;
    ArrayList<Restaurant> restaurants;
    DatabaseReference reference;
    public RestaurantsRecyclerAdapter(Context context,ArrayList<Restaurant> restaurants){
        this.restaurants=restaurants;
        this.context=context;
    }
    @NonNull
    @Override
    public RestaurantsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.restaurant_item, parent,false);
        RestaurantsRecyclerViewHolder viewHolder=new RestaurantsRecyclerViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsRecyclerViewHolder holder, int position) {
        //Context context=holder.ge
        Restaurant restaurant=this.restaurants.get(position);

         this.reference=FirebaseAccessor.getInstance().getRestaurantData("Food Corner");//reference to firebase image node of restaurant
         this.reference.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 String link=snapshot.getValue(String.class);
                 Picasso.get()
                         .load(link)// image url
                         .fit()
                         .into(holder.Image);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        Picasso.get()
                .load(restaurant.getImageUrl())// image url
                .fit()
                .into(holder.Image);

        holder.Rating.setRating(restaurant.getRating());
        holder.Name.setText(restaurant.getName());
        holder.Location.setText(restaurant.getLocation());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment menuScreen=new MenuScreen();
                FragmentManager fragmentManager = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontainer, menuScreen);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Log.d("restaurant image url",restaurant.getImageUrl());
            }
        });
        
    

    }

    @Override
    public int getItemCount() {
        return this.restaurants!=null? this.restaurants.size():0;
    }

    public class RestaurantsRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView Name,Location;
        RatingBar Rating;
        ImageView Image;
        ConstraintLayout layout;
        public RestaurantsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.restaurant_name);
            Rating=itemView.findViewById(R.id.restaurant_rating);
            Image=itemView.findViewById(R.id.restaurant_image);
            Location=itemView.findViewById(R.id.restaurant_location);
            layout=itemView.findViewById(R.id.restaurant_item_layout);

        }


    }
}
