package com.example.e_commerce_store.checkoutandpayment.payment.data;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.e_commerce_store.checkoutandpayment.payment.data.model.PaymentResult;
import com.example.e_commerce_store.checkoutandpayment.payment.data.remote.PaymentApiInterface;
import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.SharedPrefs;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepositoryImpl implements PaymentRepository {
    PaymentApiInterface paymentApiInterface;
    SharedPrefs sharedPrefs;

    @Inject
    public PaymentRepositoryImpl(PaymentApiInterface paymentApiInterface,SharedPrefs sharedPrefs) {
        this.paymentApiInterface = paymentApiInterface;
        this.sharedPrefs=sharedPrefs;
    }

    @Override
    public LiveData<Result<String>> getToken() {

        MutableLiveData<Result<String>> mutableLiveData = new MutableLiveData<>();
        paymentApiInterface.getGatewayToken().enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());

                }else{
                    Result<String> paymentRespone=new Result<String>(false,response.message(),String.valueOf(response.code()));
                    mutableLiveData.setValue(paymentRespone);
                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                Result<String> paymentRespone=new Result<String>(false,t.getMessage(),"R000");
                mutableLiveData.setValue(paymentRespone);
            }
        });

        return mutableLiveData;
    }

    @Override
    public LiveData<Result<PaymentResult>> getState(int orderId, String amount, String nonce) {
        int userId = sharedPrefs.getUserId();
        MutableLiveData<Result<PaymentResult>> mutableLiveData = new MutableLiveData<>();
        paymentApiInterface.getPaymentState( orderId,userId, amount, nonce).enqueue(new Callback<Result<PaymentResult>>() {
            @Override
            public void onResponse(Call<Result<PaymentResult>> call, Response<Result<PaymentResult>> response) {
                if (response.isSuccessful()){
                    Result<PaymentResult> result=response.body();
                    mutableLiveData.setValue(result);
                }else{
                    Result<PaymentResult> paymentRespone=new Result<PaymentResult>(false,String.valueOf(response.code()),response.message());
                    mutableLiveData.setValue(paymentRespone);
                }
            }

            @Override
            public void onFailure(Call<Result<PaymentResult>> call, Throwable t) {
                Result<PaymentResult> paymentRespone=new Result<PaymentResult>(false,t.getMessage(),"R000");
                mutableLiveData.setValue(paymentRespone);
            }
        });

        return mutableLiveData;
    }

    @Override
    public LiveData<Result<Integer>> insertOrder(JsonObject data) {
        MutableLiveData<Result<Integer>> mutableLiveData=new MutableLiveData<>();
        paymentApiInterface.makeOrder(data).enqueue(new Callback<Result<Integer>>() {
            @Override
            public void onResponse(Call<Result<Integer>> call, Response<Result<Integer>> response) {

                if (response.isSuccessful() && response != null) {
                    Result<Integer> res = response.body();
                    mutableLiveData.setValue(res);

                }else{

                    Result<Integer> res = new Result<Integer>(false,
                            response.message(),String.valueOf(response.code()));
                    mutableLiveData.setValue(res);
                }

            }

            @Override
            public void onFailure(Call<Result<Integer>> call, Throwable t) {

                Result<Integer> res = new Result<Integer>(false,
                        t.getMessage(),"R000");
                mutableLiveData.setValue(res);
            }
        });
        return mutableLiveData;
    }

    @Override
    public Order getOrder() {
        return sharedPrefs.getOrder();
    }

    @Override
    public void clearOrderAndCart() {

        sharedPrefs.deleteOrder();
        sharedPrefs.deleteCart();
    }
}
