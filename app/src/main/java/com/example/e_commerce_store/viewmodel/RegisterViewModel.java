package com.example.e_commerce_store.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.RegisterResponse;
import com.example.e_commerce_store.repository.RegisterRepository;

public class RegisterViewModel extends AndroidViewModel {
    RegisterRepository registerRepository;
    public RegisterViewModel(@NonNull Application application) {
        super(application);
        registerRepository=new RegisterRepository(application);
    }


    public LiveData<RegisterResponse> registerUser(String name,String email,String password,String apiKey){
        return registerRepository.registerNewUser(name,email,password,apiKey);
    }
}
