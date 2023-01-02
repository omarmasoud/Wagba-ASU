package com.example.wagba.GeneralScreens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wagba.Models.Order;
import com.example.wagba.R;
import com.example.wagba.Recyclers.OrdersRecyclerAdapter;
import com.example.wagba.RemoteAccess.FirebaseAccessor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderHistoryScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderHistoryScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Order> orders;
    DatabaseReference reference;
    RecyclerView ordersRecycler;

    public OrderHistoryScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderHistoryScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderHistoryScreen newInstance(String param1, String param2) {
        OrderHistoryScreen fragment = new OrderHistoryScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        orders=new ArrayList<>();
        reference= FirebaseAccessor.getInstance().getOrders();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    dataSnapshot=dataSnapshot.child("orderdata");
                    Order order=new Order();
//                    Log.d("Restaurant Name", dataSnapshot.getKey());
                    order.setDate(dataSnapshot.child("date").getValue(String.class));
                    order.setDeliveryLocation(dataSnapshot.child("deliveryLocation").getValue(String.class));
                    order.setItemCount(dataSnapshot.child("itemCount").getValue(long.class));
                    order.setPrice(dataSnapshot.child("price").getValue(long.class));
                    Log.d("order name", order.getDate());
                    orders.add(order);


                }
                setRecycler(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_history_screen, container, false);
    }
    public  void setRecycler(ArrayList<Order>orders)
    {
        ordersRecycler = getView().findViewById(R.id.orders_recycler);
        ordersRecycler.setHasFixedSize(true);
        ordersRecycler.setItemViewCacheSize(20);
        ordersRecycler.setDrawingCacheEnabled(true);
        OrdersRecyclerAdapter ordersRecyclerAdapter = new OrdersRecyclerAdapter(getContext(),orders);
        ordersRecyclerAdapter.notifyDataSetChanged();
        ordersRecycler.setAdapter(ordersRecyclerAdapter);
        ordersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}