package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.PaymentRespone;
import com.example.e_commerce_store.models.PaymentResult;
import com.example.e_commerce_store.net.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepository {


    private Application application;
    public PaymentRepository(Application application) {
        this.application = application;
    }

    public LiveData<PaymentRespone> getToken(String apiKey,String token) {

        MutableLiveData<PaymentRespone> mutableLiveData = new MutableLiveData<>();
        APIClient.getInstance().getApi().getGatewayToken(apiKey, token).enqueue(new Callback<PaymentRespone>() {
            @Override
            public void onResponse(Call<PaymentRespone> call, Response<PaymentRespone> response) {
             if (response.isSuccessful()) {
                 mutableLiveData.setValue(response.body());

             }else{
                 PaymentRespone paymentRespone=new PaymentRespone(false,response.message(),String.valueOf(response.code()));
                 mutableLiveData.setValue(paymentRespone);
             }
            }

            @Override
            public void onFailure(Call<PaymentRespone> call, Throwable t) {

                PaymentRespone paymentRespone=new PaymentRespone(false,t.getMessage(),"R000");
                mutableLiveData.setValue(paymentRespone);
            }
        });

        return mutableLiveData;
    }

    public LiveData<PaymentResult> getState(String apiKey,int orderId,int userId,String amount, String nonce,String token){
        MutableLiveData<PaymentResult> mutableLiveData = new MutableLiveData<>();
        APIClient.getInstance().getApi().getPaymentState(apiKey, orderId,userId, amount, nonce, token).enqueue(new Callback<PaymentResult>() {
            @Override
            public void onResponse(Call<PaymentResult> call, Response<PaymentResult> response) {
                if (response.isSuccessful()){
                    PaymentResult result=response.body();
                    mutableLiveData.setValue(result);
                }else{

                }
            }

            @Override
            public void onFailure(Call<PaymentResult> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }
}
