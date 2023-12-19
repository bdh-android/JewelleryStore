package com.example.e_commerce_store.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.Cart;
import com.example.e_commerce_store.repository.CartRepository;
import com.google.gson.JsonObject;

public class CartViewModel extends AndroidViewModel {

    CartRepository cartRepository;

    public CartViewModel(@NonNull Application application) {
        super(application);

        cartRepository=new CartRepository(application);
    }


    public LiveData<Cart> getCart(int id,String apiKey,String token){

        return cartRepository.getCartItems(id,apiKey,token);
    }

    public LiveData<Cart> insertCart(String apiKey, JsonObject data, String token){

        return cartRepository.insertCartItems(apiKey, data, token);
    }
}
