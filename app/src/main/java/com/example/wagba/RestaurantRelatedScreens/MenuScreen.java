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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wagba.Models.Restaurant;
import com.example.wagba.R;

import java.util.ArrayList;
import java.util.logging.Level;

import com.example.wagba.Models.MenuItem;
import com.example.wagba.Recyclers.MenuItemRecyclerAdapter;
import com.example.wagba.RemoteAccess.FirebaseAccessor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RecyclerView recyclerView;
    ArrayList<MenuItem>menuItems;
    MenuItemRecyclerAdapter adapter;
    Button cartbtn;
    String RestaurantName;


    public MenuScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuScreen newInstance(String param1, String param2) {
        MenuScreen fragment = new MenuScreen();
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
            RestaurantName=getArguments().getString("Restaurant");
        }
        menuItems=new ArrayList<>();



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        cartbtn=view.findViewById(R.id.gotocart);
//        cartbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                        Fragment cartScreen=new CartScreen();
//                        FragmentManager fragmentManager = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragmentcontainer, cartScreen);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//                    }
//
//        });


    }

    @Override
    public void onStart() {
        super.onStart();
//        setRecycler(menuItems);
        FirebaseAccessor.getInstance().getMenuOf(RestaurantName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    MenuItem menuItem=new MenuItem();
                   Log.d("Dish Name", dataSnapshot.child("name").getValue(String.class));
                   //set name
                    menuItem.setName(dataSnapshot.child("name").getValue(String.class));
                    Log.d(menuItem.getName(), "onDataChange: name ");
                    //set price
                    menuItem.setPrice(Float.parseFloat(dataSnapshot.child("price").getValue(String.class)));
                    Log.d(Float.toString(menuItem.getPrice()), "onDataChange: price ");
                    //set image url
                    menuItem.setImageUrl(dataSnapshot.child("image").getValue(String.class));
                    Log.d(menuItem.getImageUrl(), "onDataChange: imageurl ");
                    //set count
                    menuItem.setCount(1);
                    menuItems.add(menuItem);


                }
                setRecycler(menuItems);
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

        return inflater.inflate(R.layout.fragment_menu_screen, container, false);
    }
    public void setRecycler(ArrayList<MenuItem> menuItems){
        recyclerView=(RecyclerView)getView().findViewById(R.id.menu_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        adapter=new MenuItemRecyclerAdapter(getContext(),menuItems);
        adapter.notifyDataSetChanged();
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

    }
}