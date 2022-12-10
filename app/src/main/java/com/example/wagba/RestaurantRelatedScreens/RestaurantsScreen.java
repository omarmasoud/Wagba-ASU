package com.example.wagba.RestaurantRelatedScreens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wagba.R;

import java.util.ArrayList;
import java.util.Arrays;

import Models.Restaurant;
import Recyclers.RestaurantsRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantsScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantsScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantsScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantsScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantsScreen newInstance(String param1, String param2) {
        RestaurantsScreen fragment = new RestaurantsScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    ArrayList<Restaurant> restaurantArrayList=new ArrayList<>();
    Restaurant dummyrestaurant =new Restaurant();//dummy object for validating view
    RecyclerView restaurantsRecycler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        ImageView dummyimage=new ImageView(getContext());
        dummyimage.setImageResource(R.drawable.wagba);
        dummyrestaurant.setImage(dummyimage);
        dummyrestaurant.setName("My restaurant");
        dummyrestaurant.setLocation("near abdo basha");
        dummyrestaurant.setRating((float) 3.4);
        this.restaurantArrayList.add(dummyrestaurant);
        this.restaurantArrayList.add(dummyrestaurant);
        this.restaurantArrayList.add(dummyrestaurant);
        this.restaurantArrayList.add(dummyrestaurant);
        this.restaurantArrayList.add(dummyrestaurant);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_restaurants_screen, container, false);
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantsRecycler=getView().findViewById(R.id.restaurants_recycler);
        RestaurantsRecyclerAdapter restaurantsRecyclerAdapter=new RestaurantsRecyclerAdapter(restaurantArrayList);
        restaurantsRecycler.setAdapter(restaurantsRecyclerAdapter);
        restaurantsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}