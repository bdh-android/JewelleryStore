package com.example.e_commerce_store.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.PasswordResponse;
import com.example.e_commerce_store.repository.PasswordRepository;

public class PasswordViewModel extends AndroidViewModel {

    PasswordRepository passwordRepository;
    public PasswordViewModel(@NonNull Application application) {
        super(application);
        passwordRepository=new PasswordRepository(application);
    }

    public LiveData<PasswordResponse> changePassword(String apiKey,String oldPass,String newPass,String confirmPass,String token){
        return passwordRepository.changePassword(apiKey, oldPass, newPass, confirmPass, token);
    }
}
