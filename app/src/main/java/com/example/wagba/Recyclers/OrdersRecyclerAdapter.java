package com.example.wagba.Recyclers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.wagba.Models.Order;
import com.example.wagba.R;

import java.util.ArrayList;

import com.example.wagba.RemoteAccess.FirebaseAccessor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.OrdersRecyclerViewHolder> {
    Context context;
    ArrayList<Order> orders;
    DatabaseReference reference;
    public OrdersRecyclerAdapter(Context context, ArrayList<Order> orders){
        this.orders=orders;
        this.context=context;
    }
    @NonNull
    @Override
    public OrdersRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.order_item, parent,false);
        OrdersRecyclerViewHolder viewHolder=new OrdersRecyclerViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull OrdersRecyclerViewHolder holder, int position) {
        //Context context=holder.ge
        Order order=this.orders.get(position);
        holder.Gate.setText("Delivery Gate: "+order.getDeliveryLocation());
        holder.Id.setText("Date: "+order.getDate());
        holder.Status.setText("Status: "+order.getStatus());
        holder.itemCount.setText("Items : "+order.getItemCount());
        holder.Price.setText("Price : "+order.getPrice());
        order.setStatusReference(FirebaseAccessor.getInstance().getOrderStatus(order.getDate()));
        order.getStatusReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String stat=snapshot.getValue().toString();
//                Log.d("TAG", stat);
                if (stat.equals("Pending") || stat.equals("pending")) {

                    holder.Image.setImageResource(R.drawable.received);
                    holder.Status.setTextColor(context.getResources().getColor(R.color.received));
                }
                if (stat.equals("Accepted") || stat.equals("accepted"))
                {
                    holder.Image.setImageResource(R.drawable.arrows);
                    holder.Status.setTextColor(context.getResources().getColor(R.color.progress));

                }
                if (stat.equals("Delivered") || stat.equals("delivered"))
                {
                    holder.Image.setImageResource(R.drawable.delivered);
                    holder.Status.setTextColor(context.getResources().getColor(R.color.delivered));

                }


                holder.Status.setText("Status: "+snapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

    @Override
    public int getItemCount() {
        return this.orders!=null? this.orders.size():0;
    }

    public class OrdersRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView Id,Price,itemCount,Gate,Status;
        ImageView Image;
        ConstraintLayout layout;
        DatabaseReference reference;
        public OrdersRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            this.Id= itemView.findViewById(R.id.orderId);
            this.Price= itemView.findViewById(R.id.order_price);
            this.itemCount= itemView.findViewById(R.id.item_count);
            this.Gate=itemView.findViewById(R.id.gate);
            this.Status=itemView.findViewById(R.id.status);
            this.Image=itemView.findViewById(R.id.order_status_image);
            this.layout=itemView.findViewById(R.id.order_item_layout);


        }


    }
}
