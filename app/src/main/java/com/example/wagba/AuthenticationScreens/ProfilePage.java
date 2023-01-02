package com.example.wagba.AuthenticationScreens;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.wagba.Models.User;
import com.example.wagba.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilePage extends Fragment {
    FirebaseAuth firebaseAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SharedPreferences sharedPreferences;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilePage.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilePage newInstance(String param1, String param2) {
        ProfilePage fragment = new ProfilePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button Edit=view.findViewById(R.id.edit);
        Button SignOut=view.findViewById(R.id.signoutbtn);
        EditText fName,lName,genderedittext,phoneedittext,ageedittext;
        ImageView image=view.findViewById(R.id.profile_image);
        fName=view.findViewById(R.id.edittextfirstname);
        lName=view.findViewById(R.id.edittextlastname);
        genderedittext=view.findViewById(R.id.edittextgender);
        phoneedittext=view.findViewById(R.id.phonenumber);
        ageedittext=view.findViewById(R.id.edittextage);
        String fname,lname,gender,phone,age;
        firebaseAuth=FirebaseAuth.getInstance();
//        editor.putString("Email", emailtext);
//        editor.putString("FName", fName.getText().toString());
//        editor.putString("LName", lName.getText().toString());
//        editor.putString("Gender", gendertext);
//        editor.putString("Phone", phonetext);
//        editor.putString("Age", agetext);
        sharedPreferences=getContext().getSharedPreferences(User.UserSharedPref, Context.MODE_PRIVATE);
        fname=sharedPreferences.getString("FName", "first name");
        lname=sharedPreferences.getString("LName", "last name");
        gender=sharedPreferences.getString("Gender", "male");
        phone=sharedPreferences.getString("Phone", "Phone number");
        age=sharedPreferences.getString("Age", "Age");
        fName.setText(fname);
        lName.setText(lname);
        genderedittext.setText(gender);
        ageedittext.setText(age);
        phoneedittext.setText(phone);
        Log.d("gender", gender);
        if(gender.equals("male")){

            image.setImageResource(R.drawable.profile);
        }
        else{
            image.setImageResource(R.drawable.woman);
        }
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                ConstraintLayout constraintLayout = getActivity().findViewById(R.id.main_layout);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(R.id.fragmentcontainer,ConstraintSet.BOTTOM,R.id.main_layout,ConstraintSet.BOTTOM,0);
                constraintSet.applyTo(constraintLayout);
                BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottom_nav_bar);
                bottomNavigationView.setVisibility(View.INVISIBLE);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                Fragment Screen = new LoginScreen();
                transaction.replace(R.id.fragmentcontainer, Screen);
                transaction.commit();
            }
        });





    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_page, container, false);
    }
}