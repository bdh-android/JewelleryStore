package com.example.e_commerce_store.ui.shippingdetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.PaymentRespone;
import com.example.e_commerce_store.models.PaymentResult;
import com.example.e_commerce_store.repository.PaymentRepository;

public class PaymentViewModel extends AndroidViewModel {

    PaymentRepository paymentRepository;
    public PaymentViewModel(@NonNull Application application) {
        super(application);

        paymentRepository=new PaymentRepository(application);
    }

    public LiveData<PaymentRespone> getToken(String apiKey,String token){
        return  paymentRepository.getToken(apiKey, token);
    }

    public LiveData<PaymentResult> getPaymentState(String apiKey,int orderId,int userId,String amount, String nonce,String token){
        return paymentRepository.getState(apiKey, orderId,userId, amount, nonce, token);
    }
}
