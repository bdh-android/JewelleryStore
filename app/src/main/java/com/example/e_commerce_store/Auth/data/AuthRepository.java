package com.example.e_commerce_store.Auth.data;

import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.Auth.data.models.Token;
import com.example.e_commerce_store.Auth.data.models.User;
import com.example.e_commerce_store.core.models.Result;

public interface AuthRepository {

     LiveData<Result<User>> registerNewUser(String name, String email, String password);
     LiveData<Result<User>> getUser();
     LiveData<Result<Token>> loginUser(String email, String password);
}
