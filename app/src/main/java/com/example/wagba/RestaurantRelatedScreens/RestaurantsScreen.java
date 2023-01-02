package com.example.wagba.RestaurantRelatedScreens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wagba.R;

import java.util.ArrayList;

import com.example.wagba.Models.Restaurant;
import com.example.wagba.Recyclers.RestaurantsRecyclerAdapter;
import com.example.wagba.RemoteAccess.FirebaseAccessor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    FirebaseStorage storage;
    StorageReference storageReference;

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

    ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
    Restaurant dummyrestaurant = new Restaurant();//dummy object for validating view
    RecyclerView restaurantsRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAccessor.getInstance().getRestaurants().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Restaurant restaurant=new Restaurant();
                    Log.d("Restaurant Name", dataSnapshot.getKey());
                    restaurant.setName(dataSnapshot.getKey());
                    restaurant.setRating(/*(float)2.2*/Float.parseFloat(dataSnapshot.child("Rating").getValue(String.class)));
                    restaurant.setLocation(dataSnapshot.child("location").getValue(String.class));
                    restaurant.setImageUrl(dataSnapshot.child("image").getValue(String.class));
                    restaurantArrayList.add(restaurant);


                }
                setRecycler(restaurantArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_restaurants_screen, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        View fragmentcontainer=getActivity().findViewById(R.id.fragmentcontainer);
//        fragmentcontainer.setLayoutParams(new AbsListView.LayoutParams(0,0));
        getActivity().findViewById(R.id.bottom_nav_bar).setVisibility(View.VISIBLE);
        ConstraintLayout constraintLayout = getActivity().findViewById(R.id.main_layout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.fragmentcontainer,ConstraintSet.BOTTOM,R.id.bottom_nav_bar,ConstraintSet.TOP,0);
        constraintSet.applyTo(constraintLayout);


    }
    public  void setRecycler(ArrayList<Restaurant>restaurantArrayList)
    {
        restaurantsRecycler = getView().findViewById(R.id.restaurants_recycler);
        restaurantsRecycler.setHasFixedSize(true);
        restaurantsRecycler.setItemViewCacheSize(20);
        restaurantsRecycler.setDrawingCacheEnabled(true);
        RestaurantsRecyclerAdapter restaurantsRecyclerAdapter = new RestaurantsRecyclerAdapter(getContext(),restaurantArrayList);
        restaurantsRecyclerAdapter.notifyDataSetChanged();
        restaurantsRecycler.setAdapter(restaurantsRecyclerAdapter);
        restaurantsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}