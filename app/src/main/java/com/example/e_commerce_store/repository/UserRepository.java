package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.RegisterResponse;
import com.example.e_commerce_store.net.APIClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {


    private Application application;

    public UserRepository(Application application) {
        this.application = application;
    }

    public LiveData<RegisterResponse> getUser(String apiKey,String token) {

        MutableLiveData<RegisterResponse> mutableLiveData = new MutableLiveData<>();
        APIClient.getInstance().getApi().me(apiKey,token).enqueue(new Callback<RegisterResponse>() {
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
                    Toast.makeText(application, "network failure...", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(application, "conversion issue!!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return mutableLiveData;
    }
}
