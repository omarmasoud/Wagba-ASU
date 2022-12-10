package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.example.wagba.AuthenticationScreens.LoginScreen;
import com.example.wagba.GeneralScreens.PaymentScreen;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Fragment loginScreen=new LoginScreen();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontainer, loginScreen);
                fragmentTransaction.commit();
            }
        }, 1500);
    }
}