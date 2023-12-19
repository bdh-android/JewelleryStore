package com.example.e_commerce_store.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.ShippingDetails;
import com.example.e_commerce_store.repository.ShippingRepository;

import java.util.Map;

public class ShippingDetailsViewModel extends AndroidViewModel {

    ShippingRepository shippingRepository;
    public ShippingDetailsViewModel(@NonNull Application application) {
        super(application);
        shippingRepository=new ShippingRepository(application);
    }


    public LiveData<ShippingDetails> insertShippingDetails(String apiKey, Map<String,String> params,String token){
        return shippingRepository.insertShippingAddress(apiKey, params, token);
    }
}
