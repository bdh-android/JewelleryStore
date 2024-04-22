package com.example.e_commerce_store.checkoutandpayment.shippingdetails.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.e_commerce_store.R;
import com.example.e_commerce_store.core.models.Address;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.Constants;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShippingDetailsFragment extends Fragment implements View.OnClickListener {

    private ShippingDetailsViewModel mViewModel;

    private TextInputLayout l_address1,l_address2,l_country,l_state,l_city,l_phone,l_zip;
    private EditText address1,address2,country,state,city,phone,zip;
    private Button add;
    private ProgressBar progressBar;
    Map<String,String> params;


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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShippingDetailsViewModel.class);

        init(view);
        fillViewsByAddressData();
        params=new HashMap<>();

    }

    private void fillViewsByAddressData() {
        mViewModel.getShippingDetails().observe(getViewLifecycleOwner(), new Observer<Address>() {
            @Override
            public void onChanged(Address address) {
                if (address !=null){
                    address1.setText(address.getAddress1());
                    address2.setText(address.getAddress2());
                    country.setText(address.getCountry());
                    state.setText(address.getState());
                    city.setText(address.getCity());
                    phone.setText(address.getPhone_number());
                    zip.setText(address.getZip());
                }
            }
        });
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
        progressBar=view.findViewById(R.id.progressBar2);
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

        params.put(Constants.ADDRESS1,ad1);
        params.put(Constants.ADDRESS2,ad2);
        params.put(Constants.COUNTRY,co);
        params.put(Constants.STATE,st);
        params.put(Constants.CITY,ci);
        params.put(Constants.PHONENUMBER,ph);
        params.put(Constants.ZIP,zi);

        progressBar.setVisibility(View.VISIBLE);

        mViewModel.insertShippingDetails(params).observe(getViewLifecycleOwner(), new Observer<Result<Address>>() {
            @Override
            public void onChanged(Result<Address> shippingDetails) {
                if (shippingDetails!=null){
                    if (shippingDetails.getErrorNumber().equals(Constants.SUCCESS_CODE_API)) {
                        startPaymentFragment(getView());
                    }
                    else validate(shippingDetails.getErrorNumber(),shippingDetails.getMessage());
                }
                progressBar.setVisibility(View.INVISIBLE);
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

            case Constants.ERROR_CODE_VALIDATION_ADDRESS_1:
                l_address1.setError(msg);
                break;
            case Constants.ERROR_CODE_VALIDATION_ADDRESS_2:
                l_address2.setError(msg);
                break;
            case Constants.ERROR_CODE_VALIDATION_COUNTRY:
                l_country.setError(msg);
                break;
            case Constants.ERROR_CODE_VALIDATION_STATE:
                l_state.setError(msg);
                break;
            case Constants.ERROR_CODE_VALIDATION_CITY:
                l_city.setError(msg);
                break;
            case Constants.ERROR_CODE_VALIDATION_PHONE:
                l_phone.setError(msg);
                break;
            case Constants.ERROR_CODE_VALIDATION_ZIP:
                l_zip.setError(msg);
                break;
        }
    }

    private void startPaymentFragment(View view){

        Navigation.findNavController(view).navigate(R.id.action_shippingDetailsFragment_to_paymentFragment);

    }
}
