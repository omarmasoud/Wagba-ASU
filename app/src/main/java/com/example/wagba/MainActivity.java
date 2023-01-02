package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.example.wagba.AuthenticationScreens.LoginScreen;
import com.example.wagba.AuthenticationScreens.ProfilePage;
import com.example.wagba.GeneralScreens.OrderHistoryScreen;
import com.example.wagba.GeneralScreens.PaymentScreen;
import com.example.wagba.Models.User;
import com.example.wagba.RestaurantRelatedScreens.CartScreen;
import com.example.wagba.RestaurantRelatedScreens.RestaurantsScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        View fragmentcontainer=findViewById(R.id.fragmentcontainer);

//        fragmentcontainer.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        ConstraintLayout constraintLayout = findViewById(R.id.main_layout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.fragmentcontainer,ConstraintSet.BOTTOM,R.id.main_layout,ConstraintSet.BOTTOM,0);
        constraintSet.applyTo(constraintLayout);
//        SharedPreferences usersharedprefs=getApplicationContext().getSharedPreferences(User.UserSharedPref, MODE_PRIVATE);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment Screen;
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.main:
                        Screen=new RestaurantsScreen();
                        break;
                    case R.id.cart:
                        Screen=new CartScreen();
                        break;
                    case R.id.profile:
                        Screen=new ProfilePage();
                        break;
                    case R.id.orders:
                        Screen=new OrderHistoryScreen();
                        break;
                    default:
                        Screen=new RestaurantsScreen();

                }
                transaction.replace(R.id.fragmentcontainer, Screen);
                transaction.commit();
                return true;
            }
        });

       if( firebaseUser!=null)
       {
//           Log.d("mail",usersharedprefs.getString("Email","null") );
           fragment=new RestaurantsScreen();

       }
       else {
           fragment=new LoginScreen();
       }
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @SuppressLint("ResourceType")
            @Override
            public void run() {
                Log.d("mail","hello");
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontainer, fragment);

                fragmentTransaction.commit();
            }
        }, 1500);

    }
}