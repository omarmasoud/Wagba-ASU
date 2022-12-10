package Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;

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
        
    

    }

    @Override
    public int getItemCount() {
        return this.restaurants!=null? this.restaurants.size():0;
    }

    public class RestaurantsRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        RatingBar Rating;
        ImageView Image;
        public RestaurantsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.restaurant_name);
            Rating=itemView.findViewById(R.id.restaurant_rating);
            Image=itemView.findViewById(R.id.restaurant_image);
        }


    }
}
