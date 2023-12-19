package com.example.e_commerce_store.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.LoginResponse;
import com.example.e_commerce_store.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository loginRepository;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository=new LoginRepository(application);
    }

   public LiveData<LoginResponse> getLoginResponse(String email,String password,String apiKey){

       return loginRepository.loginUser(email,password,apiKey);
   }


    public LiveData<LoginResponse> logout(String apiKey,String token){

        return loginRepository.logoutUser(apiKey,token);
    }


}
