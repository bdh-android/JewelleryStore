package com.example.e_commerce_store.profile.ui.resetpassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.profile.data.ProfileRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PasswordViewModel extends ViewModel {

    ProfileRepository profileRepository;
    @Inject
    public PasswordViewModel(ProfileRepository profileRepository) {
        this. profileRepository=profileRepository;
    }

    public LiveData<Result<String>> changePassword(String oldPass, String newPass, String confirmPass){
        return profileRepository.changePassword(oldPass, newPass, confirmPass);
    }
}
