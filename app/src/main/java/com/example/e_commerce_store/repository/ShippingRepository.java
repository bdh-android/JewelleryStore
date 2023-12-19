package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.ShippingDetails;
import com.example.e_commerce_store.net.APIClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingRepository {


    private Application application;

    public ShippingRepository(Application application) {
        this.application = application;
    }

    public LiveData<ShippingDetails> insertShippingAddress(String apiKey, Map<String,String> params,String token){

        MutableLiveData<ShippingDetails> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance().getApi().insertShippingDetails(apiKey, params, token).enqueue(new Callback<ShippingDetails>() {
            @Override
            public void onResponse(Call<ShippingDetails> call, Response<ShippingDetails> response) {
                if (response.isSuccessful()&&response!=null){
                    ShippingDetails shippingDetails=response.body();
                    mutableLiveData.setValue(shippingDetails);
                }else{

                }
            }

            @Override
            public void onFailure(Call<ShippingDetails> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }



    public LiveData<ShippingDetails> getShippingDetails(String apiKey,String token){
        MutableLiveData<ShippingDetails> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance().getApi().getShippingDetails(apiKey, token).enqueue(new Callback<ShippingDetails>() {
            @Override
            public void onResponse(Call<ShippingDetails> call, Response<ShippingDetails> response) {
                if (response.isSuccessful()&&response!=null){
                    ShippingDetails shippingDetails=response.body();
                    mutableLiveData.setValue(shippingDetails);
                }else{

                }
            }

            @Override
            public void onFailure(Call<ShippingDetails> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }
}
