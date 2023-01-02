package com.example.wagba.GeneralScreens;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.wagba.Models.Cart;
import com.example.wagba.R;
import com.example.wagba.RemoteAccess.FirebaseAccessor;
import com.example.wagba.RestaurantRelatedScreens.RestaurantsScreen;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button proceed;
    TextView dueAmount;

    public PaymentScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentScreen newInstance(String param1, String param2) {
        PaymentScreen fragment = new PaymentScreen();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        proceed=view.findViewById(R.id.proceed);
        dueAmount=view.findViewById(R.id.dueamount);
        String due=Long.toString(Cart.getCart().getAmount());
        Log.d("dueamount", due);
        dueAmount.setText(due);
        RadioButton gate3 = (RadioButton) view.findViewById(R.id.Gate3);
        RadioButton gate4 = (RadioButton) view.findViewById(R.id.Gate4);
        gate3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) Cart.getCart().setDeliveryLocation("3");

            }
        });
        gate4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) Cart.getCart().setDeliveryLocation("4");

            }

        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAccessor.getInstance().addOrder();
                Fragment screen=new RestaurantsScreen();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontainer, screen);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_payment_screen, container, false);
    }
}