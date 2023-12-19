package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.LoginResponse;
import com.example.e_commerce_store.net.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {


    private Application application;

    public LoginRepository(Application application) {
        this.application = application;
    }

    public LiveData<LoginResponse> loginUser(String email,String password,String apiKey) {

        MutableLiveData<LoginResponse> mutableLiveData = new MutableLiveData<>();
        APIClient.getInstance().getApi().loginUser(email, password, apiKey).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                if (response.isSuccessful() && response != null) {
                    LoginResponse res = response.body();
                    mutableLiveData.setValue(res);

                } else {


                        LoginResponse res=new LoginResponse(false,response.message(),String.valueOf(response.code()));
                        mutableLiveData.setValue(res);

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                LoginResponse res=new LoginResponse(false,t.getMessage(),"R000");
                mutableLiveData.setValue(res);
            }
        });

        return mutableLiveData;
    }

    public LiveData<LoginResponse> logoutUser(String apiKey,String token){
        MutableLiveData<LoginResponse> mutableLiveData = new MutableLiveData<>();
        APIClient.getInstance().getApi().Logout(apiKey,token).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response != null) {
                    LoginResponse res = response.body();
                    mutableLiveData.setValue(res);
                } else {


                    LoginResponse res=new LoginResponse(false,response.message(),String.valueOf(response.code()));
                    mutableLiveData.setValue(res);

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                LoginResponse res=new LoginResponse(false,t.getMessage(),"R000");
                mutableLiveData.setValue(res);
            }
        });

        return mutableLiveData;
    }
}
