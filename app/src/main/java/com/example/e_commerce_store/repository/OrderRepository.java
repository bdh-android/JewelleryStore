package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.Order;
import com.example.e_commerce_store.models.OrderResponse;
import com.example.e_commerce_store.net.APIClient;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {


    private Application application;

    public OrderRepository(Application application) {
        this.application = application;
    }

    public LiveData<OrderResponse> insertOrder(String apiKey, JsonObject data, String token){
        MutableLiveData<OrderResponse> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance().getApi().makeOrder(apiKey, data, token).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {

                if (response.isSuccessful() && response != null) {
                    OrderResponse res = response.body();
                    mutableLiveData.setValue(res);



                }else{

                    OrderResponse res = new OrderResponse(false,response.message(),String.valueOf(response.code()));
                    mutableLiveData.setValue(res);
                }

            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

                OrderResponse res = new OrderResponse(false,t.getMessage(),"R000");
                mutableLiveData.setValue(res);
            }
        });
        return mutableLiveData;
    }


    public LiveData<List<Order>> getUserOrders(String apiKey, int userId, String token){
        MutableLiveData<List<Order>> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance().getApi().getAllUserOrders(apiKey, userId, token).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response != null) {
                    List<Order> res = response.body();
                    mutableLiveData.setValue(res);



                }else{

                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

                if (t instanceof IOException){
                    Toast.makeText(application, "network failure...", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(application, "conversion issue!!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return mutableLiveData;
    }
}
