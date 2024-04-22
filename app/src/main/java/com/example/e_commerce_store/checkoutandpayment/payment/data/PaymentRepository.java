package com.example.e_commerce_store.checkoutandpayment.payment.data;

import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.checkoutandpayment.payment.data.model.PaymentResult;
import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;
import com.google.gson.JsonObject;

public interface PaymentRepository {


     LiveData<Result<String>> getToken();

     LiveData<Result<PaymentResult>> getState(int orderId, String amount, String nonce);

     LiveData<Result<Integer>> insertOrder(JsonObject data);
      Order getOrder();
     void clearOrderAndCart();

}
