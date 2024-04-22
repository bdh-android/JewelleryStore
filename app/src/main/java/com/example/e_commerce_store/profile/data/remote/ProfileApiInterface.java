package com.example.e_commerce_store.profile.data.remote;


import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.profile.data.models.LogoutResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProfileApiInterface {

    //logout
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("auth/logout")
    Call<Result<String>> Logout();
    //reset Password
    @FormUrlEncoded
    @POST("auth/reset_password")
    Call<Result<String>> resetPassword(@Field("old_password") String oldPassword,
                               @Field("new_password")String newPassword,
                               @Field("confirm_password")String confirmPassword);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("get_all_user_orders?")
    Call<Result<List<Order>>> getAllUserOrders(@Query("user_id") int user_id);

}
