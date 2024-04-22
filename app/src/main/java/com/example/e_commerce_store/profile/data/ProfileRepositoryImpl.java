package com.example.e_commerce_store.profile.data;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.core.models.Address;
import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.SharedPrefs;
import com.example.e_commerce_store.profile.data.remote.ProfileRemoteDataSource;
import com.example.e_commerce_store.profile.ui.profile.Profile;

import java.util.List;

import javax.inject.Inject;

public class ProfileRepositoryImpl implements ProfileRepository{
    ProfileRemoteDataSource profileRemoteDataSource;
    SharedPrefs sharedPrefs;
    @Inject
    public ProfileRepositoryImpl(ProfileRemoteDataSource profileRemoteDataSource, SharedPrefs sharedPrefs) {
        this.profileRemoteDataSource = profileRemoteDataSource;
        this.sharedPrefs=sharedPrefs;
    }


    @Override
    public LiveData<Result<String>> Logout() {
        return profileRemoteDataSource.Logout();
    }

    @Override
    public LiveData<Result<String>> changePassword(String oldPass, String newPass, String confirmPass){
        return profileRemoteDataSource.changePassword(oldPass, newPass, confirmPass);
    }

    @Override
    public LiveData<Result<List<Order>>> getUserOrders() {
        int userId = sharedPrefs.getUserId();
        return profileRemoteDataSource.getUserOrders(userId);
    }

    @Override
    public LiveData<Profile> getUserProfile() {
        String userName = sharedPrefs.getUserName();
        String userEmail = sharedPrefs.getUserEmail();
        Address address = sharedPrefs.getShippingDetails();
        Profile profile = new Profile(
                userName,userEmail,
                address== null ? "__":address.getAddress1(),
                address== null ? "__":address.getAddress2(),
                address== null ? "__":address.getPhone_number(),
                address== null ? "__":address.getZip(),
                address== null ? "__":address.getCountry(),
                address== null ? "__":address.getState(),
                address== null ? "__":address.getCity()

        );
        return new MutableLiveData(profile);
    }


}
