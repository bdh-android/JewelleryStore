package com.example.e_commerce_store.checkoutandpayment.shippingdetails.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce_store.checkoutandpayment.shippingdetails.data.ShippingRepository;
import com.example.e_commerce_store.core.models.Address;
import com.example.e_commerce_store.core.models.Result;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShippingDetailsViewModel extends ViewModel {

    ShippingRepository shippingRepository;
    @Inject
    public ShippingDetailsViewModel(ShippingRepository shippingRepository) {
        this.shippingRepository=shippingRepository;
    }


    public LiveData<Result<Address>> insertShippingDetails(Map<String,String> params){
        return shippingRepository.insertShippingAddress(params);
    }
    public LiveData<Address> getShippingDetails(){
        return shippingRepository.getShippingDetails();
    }
}
