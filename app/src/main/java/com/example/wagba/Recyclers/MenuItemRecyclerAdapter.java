package com.example.wagba.Recyclers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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


public class MenuItemRecyclerAdapter extends RecyclerView.Adapter<MenuItemRecyclerAdapter.MenuItemsRecyclerViewHolder> {
    ArrayList<MenuItem> menuItems;
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

//    DatabaseReference reference;

    public MenuItemRecyclerAdapter(Context context ,ArrayList<MenuItem> menuItems) {
        Log.d("debug", "MenuItemRecyclerAdapter: ");
        this.menuItems = menuItems;
        this.context=context;
        preferences=context.getSharedPreferences(Cart.Tag,Context.MODE_PRIVATE);
        editor=preferences.edit();
    }

    @NonNull
    @Override
    public MenuItemsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("debug", "onCreateViewHolder: ");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_item, parent, false);
        MenuItemsRecyclerViewHolder viewHolder = new MenuItemsRecyclerViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemsRecyclerViewHolder holder, int position) {
        Log.d("debug", "onBindViewHolder: ");
        MenuItem menuItem = this.menuItems.get(position);
        holder.Name.setText(menuItem.getName());
        holder.Price.setText(Float.toString(menuItem.getPrice()) + " LE");
        holder.count.setText(Integer.toString(menuItem.getCount()));
        holder.Image.setImageResource(R.drawable.profile);





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

                        break;
                    default:
                        break;
                }
                holder.Price.setText(Float.toString(menuItem.getPrice()*menuItem.getCount()) + " LE");
            }
        };
        holder.add.setOnClickListener(listener);
        holder.remove.setOnClickListener(listener);



            holder.addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart.getCart().addToCart(menuItem);
                    Toast.makeText(context, Integer.toString(menuItem.getCount())+" of "+menuItem.getName() +" added to Cart",Toast.LENGTH_SHORT).show();
                    Cart.getCart().storeData(context);
                }
            });
    }

    @Override
    public int getItemCount() {
        Log.d("debug", "getItemCount: ");
        Log.d("itemcount", Integer.toString(menuItems.size()));
        return this.menuItems.size();
    }

    public class MenuItemsRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView Name, Price, count;
        ImageView Image;
        Button add, remove;
//        ConstraintLayout layout;
        Button addtocart;

        public MenuItemsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("debug", "menuitemrecyclerviewholder: ");
            Name = itemView.findViewById(R.id.orderId);
            Image = itemView.findViewById(R.id.item_image);
            Price = itemView.findViewById(R.id.item_price);
            count = itemView.findViewById(R.id.item_count);
            add = itemView.findViewById(R.id.increment);
            remove = itemView.findViewById(R.id.decrement);
//            layout = itemView.findViewById(R.id.menu_item_layout);
            addtocart=itemView.findViewById(R.id.addtocartbtn);

        }


    }
}

