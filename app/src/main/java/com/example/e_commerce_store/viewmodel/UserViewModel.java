package com.example.e_commerce_store.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.RegisterResponse;
import com.example.e_commerce_store.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    UserRepository userRepository;
    public UserViewModel(@NonNull Application application) {
        super(application);

        userRepository=new UserRepository(application);
    }

    public LiveData<RegisterResponse> getMe(String apiKey,String token){
       return userRepository.getUser(apiKey,token);
    }
}
