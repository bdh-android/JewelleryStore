package com.example.e_commerce_store.Auth.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce_store.Auth.data.AuthRepository;
import com.example.e_commerce_store.Auth.data.models.Token;
import com.example.e_commerce_store.Auth.data.models.User;
import com.example.e_commerce_store.core.models.Result;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel extends ViewModel {
    AuthRepository authRepository;

    @Inject
    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<Result<Token>> getLoginResponse(String email, String password){

        return authRepository.loginUser(email,password);
    }
    public LiveData<Result<User>> registerUser(String name, String email,String password){
        return authRepository.registerNewUser(name,email,password);
    }

    public LiveData<Result<User>> getUser(){
        return authRepository.getUser();
    }



}
