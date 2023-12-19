package com.example.e_commerce_store.ui.shippingdetails;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerce_store.R;
import com.example.e_commerce_store.models.ShippingDetails;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.viewmodel.ShippingDetailsViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class ShippingDetailsFragment extends Fragment implements View.OnClickListener {

    private ShippingDetailsViewModel mViewModel;

    private TextInputLayout l_address1,l_address2,l_country,l_state,l_city,l_phone,l_zip;
    private EditText address1,address2,country,state,city,phone,zip;
    private Button add;
    Map<String,String> params;
    String token;
    String user_id;

    public static ShippingDetailsFragment newInstance() {
        return new ShippingDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shipping_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShippingDetailsViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        token= SaveToSharedPreferance.getToken(getActivity());
        params=new HashMap<>();
        user_id=String.valueOf(SaveToSharedPreferance.getUserId(getActivity()));

    }


    private void init(View view){
        l_address1=view.findViewById(R.id.layout_address1);
        l_address2=view.findViewById(R.id.layout_address2);
        l_country=view.findViewById(R.id.layout_country);
        l_state=view.findViewById(R.id.layout_state);
        l_city=view.findViewById(R.id.layout_city);
        l_phone=view.findViewById(R.id.layout_phone_number);
        l_zip=view.findViewById(R.id.layout_zip);
        address1=view.findViewById(R.id.address1);
        address2=view.findViewById(R.id.address2);
        country=view.findViewById(R.id.country);
        state=view.findViewById(R.id.state);
        city=view.findViewById(R.id.city);
        phone=view.findViewById(R.id.phone_number);
        zip=view.findViewById(R.id.zip);
        add=view.findViewById(R.id.add_shippingDetails);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if (id==R.id.add_shippingDetails){
            sendShippingDetails();
        }
    }

    private void sendShippingDetails(){
        String ad1=address1.getText().toString();
        String ad2=address2.getText().toString();
        String co=country.getText().toString();
        String st=state.getText().toString();
        String ci=city.getText().toString();
        String ph=phone.getText().toString();
        String zi=zip.getText().toString();

        params.put("user_id",user_id);
        params.put("address1",ad1);
        params.put("address2",ad2);
        params.put("country",co);
        params.put("state",st);
        params.put("city",ci);
        params.put("phone_number",ph);
        params.put("zip",zi);


           mViewModel.insertShippingDetails(Constants.API_KEY,params,"Bearer "+token).observe(this, new Observer<ShippingDetails>() {
            @Override
            public void onChanged(ShippingDetails shippingDetails) {
                if (shippingDetails!=null){
                    if (shippingDetails.getErrorNumber().equals("S000")) {
                        startPaymentFragment();
                        Toast.makeText(getActivity(), shippingDetails.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else validate(shippingDetails.getErrorNumber(),shippingDetails.getMsg());
                }
            }
        });
    }

    private void validate(String errorNumber,String msg) {

        l_address1.setError(null);
        l_address2.setError(null);
        l_country.setError(null);
        l_state.setError(null);
        l_city.setError(null);
        l_phone.setError(null);
        l_zip.setError(null);

        switch (errorNumber){

            case "E004":
                l_address1.setError(msg);
                break;
            case "E005":
                l_address2.setError(msg);
                break;
            case "E006":
                l_country.setError(msg);
                break;
            case "E007":
                l_state.setError(msg);
                break;
            case "E008":
                l_city.setError(msg);
                break;
            case "E009":
                l_phone.setError(msg);
                break;
            case "E0010":
                l_zip.setError(msg);
                break;
        }
    }

    private void startPaymentFragment(){
        Fragment fragment = new PaymentFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}
