package com.example.wagba.Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.Models.Cart;
import com.example.wagba.R;

import java.util.ArrayList;

import com.example.wagba.Models.MenuItem;
import com.squareup.picasso.Picasso;


public class CartItemRecyclerAdapter extends RecyclerView.Adapter<CartItemRecyclerAdapter.CartItemsRecyclerViewHolder> {
    ArrayList<MenuItem> cartItems;
    Context context;
    public CartItemRecyclerAdapter(Context context ) {
        Cart.getCart().loadData(context);
        this.cartItems = Cart.getCart().getItems();
        this.context= context;
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

        holder.Name.setText(menuItem.getName());
        holder.Price.setText(Float.toString(menuItem.getCount()*menuItem.getPrice()) + " LE");
        holder.count.setText(Integer.toString(menuItem.getCount()));
        try{Picasso.get().load(menuItem.getImageUrl()).fit().placeholder(R.drawable.dish).into(holder.Image);}
        catch (Exception e){}

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.count.getText().toString());
                switch (view.getId()) {

                    case R.id.increment:
                        holder.count.setText(Integer.toString(count + 1));
                        menuItem.setCount(count+1);
                        break;
                    case R.id.decrement:
                        if (count > 1) {
                            holder.count.setText(Integer.toString(count - 1));
                            menuItem.setCount(count-1);
                        } else {
                            holder.count.setText(Integer.toString(count));
                        }
                        holder.Price.setText(Float.toString(menuItem.getCount()*menuItem.getPrice()) + " LE");
                        break;
                    default:
                        break;
                }
                holder.Price.setText(Float.toString(menuItem.getCount()*menuItem.getPrice()) + " LE");
                notifyItemRemoved(position);
                Cart.getCart().storeData(context);

            }
        };
        holder.add.setOnClickListener(listener);
        holder.remove.setOnClickListener(listener);
        holder.movetotrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart.getCart().removeItem(menuItem);
                Toast.makeText(context,menuItem.getName()+" items have been removed from cart",Toast.LENGTH_SHORT).show();
                notifyItemRemoved(position);
                Cart.getCart().storeData(context);
//                notifyItemRangeChanged(,Cart.getCart().getItems().size());

            }
        });



    }

    @Override
    public int getItemCount() {
        return this.cartItems != null ? this.cartItems.size() : 0;
    }

    public class CartItemsRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Price, count;
        ImageView Image;
        Button add, remove,movetotrash;

        public CartItemsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.orderId);
            Image = itemView.findViewById(R.id.item_image);
            Price = itemView.findViewById(R.id.item_price);
            count = itemView.findViewById(R.id.item_count);
            add = itemView.findViewById(R.id.increment);
            remove = itemView.findViewById(R.id.decrement);
            movetotrash=itemView.findViewById(R.id.removefromcartbtn);

        }


    }
}

