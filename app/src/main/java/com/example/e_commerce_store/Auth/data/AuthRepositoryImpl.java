package com.example.e_commerce_store.Auth.data;

import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.Auth.data.models.Token;
import com.example.e_commerce_store.Auth.data.models.User;
import com.example.e_commerce_store.Auth.data.remote.AuthRemoteDataSource;
import com.example.e_commerce_store.core.models.Result;

import javax.inject.Inject;

public class AuthRepositoryImpl implements AuthRepository {

    private AuthRemoteDataSource authRemoteDataSource;

    @Inject
    public AuthRepositoryImpl(AuthRemoteDataSource authRemoteDataSource) {
        this.authRemoteDataSource = authRemoteDataSource;
    }

    @Override
    public LiveData<Result<User>> registerNewUser(String name, String email, String password) {

        return authRemoteDataSource.createNewUser(name, email, password);
    }

    @Override
    public LiveData<Result<User>> getUser() {
        return authRemoteDataSource.getUser();
    }

    @Override
    public LiveData<Result<Token>> loginUser(String email, String password) {
        return authRemoteDataSource.loginUser(email,password);
    }


}
