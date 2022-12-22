package com.example.wagba.RestaurantRelatedScreens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wagba.GeneralScreens.PaymentScreen;
import com.example.wagba.R;

import java.util.ArrayList;

import com.example.wagba.Models.MenuItem;
import com.example.wagba.Recyclers.CartItemRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static CartScreen newInstance(String param1, String param2) {
        CartScreen fragment = new CartScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    RecyclerView recyclerView;
    CartItemRecyclerAdapter adapter;
    ArrayList<MenuItem> menuItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        menuItems=new ArrayList<>();
        MenuItem DummyMenuItem=new MenuItem();
        ImageView dummyimage=new ImageView(getContext());
        dummyimage.setImageResource(R.drawable.wagba);
        DummyMenuItem.setImage(dummyimage);
        DummyMenuItem.setName("Shawerma Crepe");
        DummyMenuItem.setCount(1);
        DummyMenuItem.setPrice(60);

        for (int i = 0 ;i<20; i++)
        {
            menuItems.add(DummyMenuItem);
        }


        adapter=new CartItemRecyclerAdapter(menuItems);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_screen, container, false);
    }
    Button gotopayment;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.cart_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() ));
        gotopayment=view.findViewById(R.id.gotopayment);
        gotopayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment payment=new PaymentScreen();
                FragmentManager fragmentManager = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontainer, payment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
}