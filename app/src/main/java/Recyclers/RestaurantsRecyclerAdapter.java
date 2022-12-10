package Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
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

import com.example.wagba.AuthenticationScreens.LoginScreen;
import com.example.wagba.R;
import com.example.wagba.RestaurantRelatedScreens.MenuScreen;

import java.util.ArrayList;

import Models.Restaurant;

public class RestaurantsRecyclerAdapter extends RecyclerView.Adapter<RestaurantsRecyclerAdapter.RestaurantsRecyclerViewHolder> {
    ArrayList<Restaurant> restaurants;
    public RestaurantsRecyclerAdapter(ArrayList<Restaurant> restaurants){
        this.restaurants=restaurants;
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
        Restaurant restaurant=this.restaurants.get(position);
        holder.Image=restaurant.getImage();
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
