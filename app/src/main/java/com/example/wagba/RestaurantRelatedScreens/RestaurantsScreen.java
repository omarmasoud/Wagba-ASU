package com.example.wagba.RestaurantRelatedScreens;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wagba.R;

import java.util.ArrayList;

import com.example.wagba.Models.Restaurant;
import com.example.wagba.Recyclers.RestaurantsRecyclerAdapter;
import com.example.wagba.RemoteAccess.FirebaseAccessor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
//        ImageView dummyimage = new ImageView(getContext());
        //////////////////////
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference("kfc.png");
//        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
////                Glide.with(getContext())
////                        .load(uri.toString()) // image url
////                        .placeholder(dummyimage.getDrawable()) // any placeholder to load at start
////                        .error(R.drawable.wagba)  // any image in case of error
////                        .override(200, 200) // resizing
////                        .centerCrop()
////                        .into(dummyimage);
//                dummyrestaurant.setImageUrl(uri.toString());
//                Log.d("Image URI", uri.toString());
//
//            }
//        });
//        storageReference=storageReference.child("papalogo.png");
//
//        final long ONE_MEGABYTE = 1024 * 1024;
//        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                // Data for "images/island.jpg" is returns, use this as needed
//                Toast.makeText(getContext(), "loaded", Toast.LENGTH_LONG).show();
//                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                dummyimage.setImageBitmap(bmp);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(getContext(), "failed to load", Toast.LENGTH_LONG).show();
//
//                // Handle any errors
////                dummyimage.setImageResource(R.drawable.wagba);
//            }
//        });
        //////



//        dummyrestaurant.setImage(dummyimage);
       // String DummyUrl =FirebaseAccessor.getInstance().getRestaurantData("Food Corner").getImageUrl();
        //dummyrestaurant.setImageUrl(DummyUrl);
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
        restaurantsRecycler = getView().findViewById(R.id.restaurants_recycler);
        restaurantsRecycler.setHasFixedSize(true);
        restaurantsRecycler.setItemViewCacheSize(20);
        restaurantsRecycler.setDrawingCacheEnabled(true);
        RestaurantsRecyclerAdapter restaurantsRecyclerAdapter = new RestaurantsRecyclerAdapter(getContext(),restaurantArrayList);
        restaurantsRecyclerAdapter.notifyDataSetChanged();
        restaurantsRecycler.setAdapter(restaurantsRecyclerAdapter);
        restaurantsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseAccessor.getInstance().getRestaurantData("Papa Johns");
    }
}