package com.example.wagba.Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;

import java.util.ArrayList;

import com.example.wagba.Models.MenuItem;


public class CartItemRecyclerAdapter extends RecyclerView.Adapter<CartItemRecyclerAdapter.CartItemsRecyclerViewHolder> {
    ArrayList<MenuItem> cartItems;

    public CartItemRecyclerAdapter(ArrayList<MenuItem> menuItems) {
        this.cartItems = menuItems;
    }

    @NonNull
    @Override
    public CartItemsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        CartItemsRecyclerViewHolder viewHolder = new CartItemsRecyclerViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsRecyclerViewHolder holder, int position) {
        MenuItem menuItem = this.cartItems.get(position);
        holder.Image = menuItem.getImage();
//        holder.Rating.setRating(menuItem.getRating());
        holder.Name.setText(menuItem.getName());
        holder.Price.setText(Float.toString(menuItem.getPrice()) + " LE");
        holder.count.setText(Integer.toString(menuItem.getCount()));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.count.getText().toString());
                switch (view.getId()) {

                    case R.id.increment:
                        holder.count.setText(Integer.toString(count + 1));
                        break;
                    case R.id.decrement:
                        if (count > 0) {
                            holder.count.setText(Integer.toString(count - 1));
                        } else {
                            holder.count.setText("0");
                        }

                        break;
                    default:
                        break;
                }
            }
        };
        holder.add.setOnClickListener(listener);
        holder.remove.setOnClickListener(listener);



    }

    @Override
    public int getItemCount() {
        return this.cartItems != null ? this.cartItems.size() : 0;
    }

    public class CartItemsRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Price, count;
        RatingBar Rating;
        ImageView Image;
        Button add, remove;
        ConstraintLayout layout;

        public CartItemsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.item_name);
            Rating = itemView.findViewById(R.id.item_rating);
            Image = itemView.findViewById(R.id.item_image);
            Price = itemView.findViewById(R.id.item_price);
            count = itemView.findViewById(R.id.item_count);
            add = itemView.findViewById(R.id.increment);
            remove = itemView.findViewById(R.id.decrement);
            layout = itemView.findViewById(R.id.cart_item_layout);

        }


    }
}

