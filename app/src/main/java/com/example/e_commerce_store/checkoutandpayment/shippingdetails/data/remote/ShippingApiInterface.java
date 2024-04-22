package com.example.e_commerce_store.checkoutandpayment.shippingdetails.data.remote;

import com.example.e_commerce_store.core.models.Address;
import com.example.e_commerce_store.core.models.Result;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ShippingApiInterface {

    @FormUrlEncoded
    @POST("save_user_shipping_details")
    Call<Result<Address>> insertShippingDetails(@FieldMap Map<String,String> params);

    @Headers({"Content-Type:application/json"})
    @GET("get_user_shipping_details")
    Call<Result<Address>> getShippingDetails();

}
