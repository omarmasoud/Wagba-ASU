package com.example.wagba.AuthenticationScreens;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wagba.Models.User;
import com.example.wagba.R;
import com.example.wagba.RemoteAccess.FirebaseAccessor;
import com.example.wagba.RestaurantRelatedScreens.RestaurantsScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpScreen extends Fragment {
    FirebaseAuth authenticator;
    SharedPreferences userSharePrefs;
    SharedPreferences.Editor editor;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpScreen newInstance(String param1, String param2) {
        SignUpScreen fragment = new SignUpScreen();
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
    }

    @Override
    public void onStart() {
        super.onStart();
        authenticator=FirebaseAuth.getInstance();
        userSharePrefs= getActivity().getApplicationContext().getSharedPreferences(User.UserSharedPref, Context.MODE_PRIVATE);
        editor=userSharePrefs.edit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button Signup=view.findViewById(R.id.signup);
        EditText email, password,rePassword,fName,lName,phone,age;
        EditText gender;
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        rePassword=view.findViewById(R.id.reenterpassword);
        fName=view.findViewById(R.id.edittextfirstname);
        lName=view.findViewById(R.id.edittextlastname);
        gender= view.findViewById(R.id.genders);
        phone=view.findViewById(R.id.phonenumber);
        age=view.findViewById(R.id.edittextage);
        String pattern= "^(.+)@(eng.asu.edu.eg)";
        String passwordPattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtext=email.getText().toString();
                String passwordText=password.getText().toString();
                String reenterPasswordText=rePassword.getText().toString();
                String fnameText=fName.getText().toString();
                String lnameText=lName.getText().toString();
                String phonetext=phone.getText().toString();
                String gendertext=gender.getText().toString();
                String agetext=age.getText().toString();
                boolean emailCheck=Pattern.compile(pattern).matcher(emailtext).matches();
                boolean passwordCheck=Pattern.compile(passwordPattern).matcher(passwordText).matches();
                boolean passwordsMatchCheck = (passwordText.equals(reenterPasswordText));

                if(!emailCheck)
                {
                    Snackbar snackbar= Snackbar.make(view,"enter an email within Eng ASU domain",Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.proceed, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                    return;
                }
                if(!passwordCheck)
                {
                    Snackbar snackbar= Snackbar.make(view,"enter 8 characters long password with 1 digit at least and an UpperCase",Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.proceed, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                    return;
                }
                if(!passwordsMatchCheck)
                {
                    Snackbar snackbar = Snackbar.make(view, "Passwords dont match", Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.proceed, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                    return;
                }


                authenticator.createUserWithEmailAndPassword(emailtext, passwordText)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("SignUp", "createUserWithEmail:success");
                                    Toast.makeText(view.getContext(), "Authentication Success.", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = authenticator.getCurrentUser();
                                    editor.putString("Email", emailtext);
                                    editor.putString("FName", fName.getText().toString());
                                    editor.putString("LName", lName.getText().toString());
                                    editor.putString("Gender", gendertext);
                                    editor.putString("Phone", phonetext);
                                    editor.putString("Age", agetext);

                                    editor.commit();
                                    String UID =authenticator.getUid();
                                    User newuser=new User(emailtext, fnameText, lnameText, UID,gendertext,agetext);
                                    FirebaseAccessor.getInstance().addUser(newuser);


                                    Fragment screen=new RestaurantsScreen();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragmentcontainer, screen);
                                    fragmentTransaction.commit();
                                    //updateUI(user);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(view.getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}