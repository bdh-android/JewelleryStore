package com.example.e_commerce_store.checkoutandpayment.shippingdetails.data;

import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.core.models.Address;
import com.example.e_commerce_store.core.models.Result;

import java.util.Map;

public interface ShippingRepository {

    LiveData<Result<Address>> insertShippingAddress(Map<String,String> params);
    LiveData<Address> getShippingDetails();

}
