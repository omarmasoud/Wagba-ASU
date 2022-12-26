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
import com.example.wagba.RestaurantRelatedScreens.MenuScreen;
import com.example.wagba.RestaurantRelatedScreens.RestaurantsScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginScreen extends Fragment {
    SharedPreferences usersharedprefs;
    SharedPreferences.Editor editor;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button login, signup;
    FirebaseAuth authenticator;

    public LoginScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginScreen newInstance(String param1, String param2) {
        LoginScreen fragment = new LoginScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersharedprefs=getActivity().getApplicationContext().getSharedPreferences(User.UserSharedPref, Context.MODE_PRIVATE);
        editor=usersharedprefs.edit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login_screen, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        authenticator=FirebaseAuth.getInstance();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login = (Button) getView().findViewById(R.id.login_btn);
        signup=(Button) getView().findViewById(R.id.sign_up_btn);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment screen;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (view.getId()) {
                    case R.id.login_btn:
                        screen = new RestaurantsScreen();
                        break;
                    case R.id.sign_up_btn:
                        screen = new SignUpScreen();
                    default:
                        screen = new SignUpScreen();
                }


                fragmentTransaction.replace(R.id.fragmentcontainer, screen);
                if(view.getId()==R.id.sign_up_btn)
                    fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
        signup.setOnClickListener(listener);


        Button Login=view.findViewById(R.id.login_btn);
        EditText email, password;
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);

        String pattern= "^(.+)@(eng.asu.edu.eg)";
        String passwordPattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtext=email.getText().toString();
                String passwordText=password.getText().toString();
                boolean emailCheck= Pattern.compile(pattern).matcher(emailtext).matches();
                boolean passwordCheck=Pattern.compile(passwordPattern).matcher(passwordText).matches();
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
                authenticator.signInWithEmailAndPassword(emailtext, passwordText)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("SignUp", "createUserWithEmail:success");
                                    Toast.makeText(view.getContext(), "Login Successful.", Toast.LENGTH_SHORT).show();

                                    FirebaseUser user = authenticator.getCurrentUser();
                                    //updateUI(user);
                                    Fragment screen=new RestaurantsScreen();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragmentcontainer, screen);
                                    fragmentTransaction.commit();
                                    editor.putString("Email", emailtext);
                                    Log.d("mail", emailtext);
//                                    editor.putString("FName", fName.getText().toString());
//                                    editor.putString("LName", lName.getText().toString());


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(view.getContext(), "Login failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}