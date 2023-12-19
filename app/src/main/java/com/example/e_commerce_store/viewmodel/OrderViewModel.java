package com.example.e_commerce_store.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.Order;
import com.example.e_commerce_store.models.OrderResponse;
import com.example.e_commerce_store.repository.OrderRepository;
import com.google.gson.JsonObject;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    OrderRepository orderRepository;
    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository=new OrderRepository(application);
    }


    public LiveData<OrderResponse> makeOrder(String apiKey, JsonObject data, String token){

        return  orderRepository.insertOrder(apiKey, data, token);
    }
    public LiveData<List<Order>> getUserOrders(String apiKey, int userId, String token){

        return  orderRepository.getUserOrders(apiKey, userId, token);
    }
}
