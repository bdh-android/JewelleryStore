package com.example.e_commerce_store.profile.data.remote;

import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;

import java.util.List;

public interface ProfileRemoteDataSource {
    LiveData<Result<String>> Logout();
    LiveData<Result<String>> changePassword(String oldPass, String newPass, String confirmPass);
    LiveData<Result<List<Order>>> getUserOrders(int userId);
}
