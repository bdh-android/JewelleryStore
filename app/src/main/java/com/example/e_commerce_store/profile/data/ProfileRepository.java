package com.example.e_commerce_store.profile.data;

import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.profile.ui.profile.Profile;

import java.util.List;

public interface ProfileRepository {
    LiveData<Result<String>> Logout();
    LiveData<Result<String>> changePassword(String oldPass, String newPass, String confirmPass);
    LiveData<Result<List<Order>>> getUserOrders();
    LiveData<Profile> getUserProfile();
}
