package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.RegisterResponse;
import com.example.e_commerce_store.net.APIClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {

    private Application application;

    public RegisterRepository(Application application) {
        this.application = application;
    }

    public LiveData<RegisterResponse> registerNewUser(String name,String email, String password, String apiKey) {

        MutableLiveData<RegisterResponse> mutableLiveData = new MutableLiveData<>();
        APIClient.getInstance().getApi().createNewUser(name,email, password, apiKey).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response != null) {
                    RegisterResponse res = response.body();
                    mutableLiveData.setValue(res);
                }else {

                    RegisterResponse res=new RegisterResponse(false,response.message(),String.valueOf(response.code()));
                    mutableLiveData.setValue(res);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                RegisterResponse res=new RegisterResponse(false,t.getMessage(),"R000");
                mutableLiveData.setValue(res);
                if (t instanceof IOException){
                    //Toast.makeText(application, "network failure...", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(application, "conversion issue!!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return mutableLiveData;
    }
}
