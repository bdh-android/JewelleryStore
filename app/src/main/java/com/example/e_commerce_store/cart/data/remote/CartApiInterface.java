package com.example.e_commerce_store.cart.data.remote;

import com.example.e_commerce_store.core.models.CartItem;
import com.example.e_commerce_store.core.models.Result;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartApiInterface {
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("get_cart_items_by_user_id/{User_id}")
    Call<Result<List<CartItem>>> getCartItems(@Path("User_id") int id);
    @Headers({"Content-Type:application/json"})
    @POST("insert_cart")
    Call<Result<String>> insertItemsIntoCart(@Body JsonObject data);
}
