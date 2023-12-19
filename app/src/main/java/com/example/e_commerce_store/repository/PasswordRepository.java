package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.PasswordResponse;
import com.example.e_commerce_store.net.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordRepository {


    Application application;

    public PasswordRepository(Application application) {
        this.application = application;
    }
       public  LiveData<PasswordResponse> changePassword(String apiKey,String oldPass,String newPass,String confirmPass,String token){
            MutableLiveData<PasswordResponse> mutableLiveData=new MutableLiveData<>();

            APIClient.getInstance().getApi().resetPassword(apiKey, oldPass, newPass, confirmPass, token).enqueue(new Callback<PasswordResponse>() {
                @Override
                public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                    if (response.isSuccessful()){
                        PasswordResponse passwordResponse=response.body();
                        mutableLiveData.setValue(passwordResponse);
                    }else{

                        PasswordResponse passwordResponse=new PasswordResponse(false,response.message(),String.valueOf(response.code()));
                        mutableLiveData.setValue(passwordResponse);
                    }
                }

                @Override
                public void onFailure(Call<PasswordResponse> call, Throwable t) {

                    PasswordResponse passwordResponse=new PasswordResponse(false,t.getMessage(),"R000");
                    mutableLiveData.setValue(passwordResponse);
                }
            });


            return mutableLiveData;

    }
}
