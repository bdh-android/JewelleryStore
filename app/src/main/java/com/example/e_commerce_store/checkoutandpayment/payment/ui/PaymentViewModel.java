package com.example.e_commerce_store.checkoutandpayment.payment.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce_store.checkoutandpayment.payment.data.PaymentRepository;
import com.example.e_commerce_store.checkoutandpayment.payment.data.model.PaymentResult;
import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PaymentViewModel extends ViewModel {

    PaymentRepository paymentRepository;
    @Inject
    public PaymentViewModel(PaymentRepository paymentRepository) {
        this.paymentRepository=paymentRepository;
    }

    public LiveData<Result<String>> getToken(){
        return  paymentRepository.getToken();
    }

    public LiveData<Result<PaymentResult>> getPaymentState(int orderId, String amount, String nonce){
        return paymentRepository.getState(orderId, amount, nonce);
    }

    public LiveData<Result<Integer>> makeOrder(JsonObject data){

        return  paymentRepository.insertOrder(data);
    }
    Order getOrder(){
        return paymentRepository.getOrder() ;
    }

    void clearOrderAndCart(){
        Thread thread=new Thread(){
            @Override
            public void run() {
                paymentRepository.clearOrderAndCart();
            }
        };
        thread.start();

    }
}
