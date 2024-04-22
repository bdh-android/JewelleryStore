package com.example.e_commerce_store.profile.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.profile.data.ProfileRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileViewModel extends ViewModel {

    ProfileRepository profileRepository;
    @Inject
    public ProfileViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public LiveData<Result<String>> Logout(){
        return profileRepository.Logout();
    }
    public LiveData<Profile> getUserProfile(){
        return profileRepository.getUserProfile();
    }

}
