package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.Cart;
import com.example.e_commerce_store.net.APIClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {


    private Application application;

    public CartRepository(Application application) {
        this.application = application;
    }

    public LiveData<Cart> getCartItems(int id,String apiKey,String token){
        MutableLiveData<Cart> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance().getApi().getCartItems(id,apiKey,token).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful() && response != null) {
                    Cart res = response.body();
                    mutableLiveData.setValue(res);



                }else{
                    Cart res =new Cart(false,response.message(),String.valueOf(response.code()));
                    mutableLiveData.setValue(res);

                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

                Cart res =new Cart(false,t.getMessage(),"R000");
                mutableLiveData.setValue(res);
            }
        });
        return mutableLiveData;
    }

    public LiveData<Cart> insertCartItems(String apiKey, JsonObject data, String token){
        MutableLiveData<Cart> mutableLiveData=new MutableLiveData<>();
           APIClient.getInstance().getApi().insertItemsIntoCart(apiKey, data, token).enqueue(new Callback<Cart>() {
               @Override
               public void onResponse(Call<Cart> call, Response<Cart> response) {
                   if (response.isSuccessful() && response != null) {
                       Cart res = response.body();
                       mutableLiveData.setValue(res);



                   }else{

                       Cart res =new Cart(false,response.message(),String.valueOf(response.code()));
                       mutableLiveData.setValue(res);
                   }
               }



               @Override
               public void onFailure(Call<Cart> call, Throwable t) {

                   Cart res =new Cart(false,t.getMessage(),"R000");
                   mutableLiveData.setValue(res);
               }
           });
        return mutableLiveData;
    }
}
