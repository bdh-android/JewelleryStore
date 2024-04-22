package com.example.e_commerce_store.checkoutandpayment.payment.data.remote;

import com.example.e_commerce_store.checkoutandpayment.payment.data.model.PaymentResult;
import com.example.e_commerce_store.core.models.Result;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PaymentApiInterface {

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("checkout")
    Call<Result<String>> getGatewayToken();
    //send nonce and amount(total money) to your server to complete payment process and get the success result
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("payment")
    Call<Result<PaymentResult>> getPaymentState(@Query("order_id") int orderId,
                                        @Query("user_id") int userId,
                                        @Query("amount") String amount,
                                        @Query("nonce")String nonce);



    @Headers({"Content-Type:application/json"})
    @POST("make_order")
    Call<Result<Integer>> makeOrder(@Body JsonObject data);


}
