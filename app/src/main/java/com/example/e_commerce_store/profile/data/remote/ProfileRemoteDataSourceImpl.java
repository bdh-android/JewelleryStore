package com.example.e_commerce_store.profile.data.remote;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRemoteDataSourceImpl implements ProfileRemoteDataSource {

    ProfileApiInterface profileApiInterface;

    @Inject
    public ProfileRemoteDataSourceImpl(ProfileApiInterface profileApiInterface) {
        this.profileApiInterface = profileApiInterface;
    }

    @Override
    public LiveData<Result<String>> Logout() {
        MutableLiveData<Result<String>> mutableLiveData = new MutableLiveData<>();
        profileApiInterface.Logout().enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                if (response.isSuccessful() && response != null) {
                    Result<String> res = response.body();
                    mutableLiveData.setValue(res);
                } else {


                    Result<String> res = new Result<String>(
                            false, String.valueOf(response.code()), response.message());
                    mutableLiveData.setValue(res);

                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {

                Result<String> res = new Result<String>(false
                        , "R000", t.getMessage());
                mutableLiveData.setValue(res);
            }
        });

        return mutableLiveData;
    }

    @Override
    public LiveData<Result<String>> changePassword(String oldPass, String newPass, String confirmPass) {
        MutableLiveData<Result<String>> mutableLiveData = new MutableLiveData<>();

        profileApiInterface.resetPassword(oldPass, newPass, confirmPass).enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                if (response.isSuccessful()) {
                    Result<String> passwordResponse = response.body();
                    mutableLiveData.setValue(passwordResponse);
                } else {

                    Result<String> passwordResponse = new Result<String>(
                            false, String.valueOf(response.code()), response.message());
                    mutableLiveData.setValue(passwordResponse);
                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {

                Result<String> passwordResponse = new Result<String>(
                        false, "R000", t.getMessage());
                mutableLiveData.setValue(passwordResponse);
            }
        });


        return mutableLiveData;
    }

    @Override
    public LiveData<Result<List<Order>>> getUserOrders(int userId) {
        MutableLiveData<Result<List<Order>>> mutableLiveData = new MutableLiveData<>();
        profileApiInterface.getAllUserOrders(userId).enqueue(new Callback<Result<List<Order>>>() {
            @Override
            public void onResponse(Call<Result<List<Order>>> call, Response<Result<List<Order>>> response) {
                if (response.isSuccessful() && response != null) {
                    Result<List<Order>> res = response.body();
                    mutableLiveData.setValue(res);
                } else {
                    Result<List<Order>> res = new Result<List<Order>>(
                            false, String.valueOf(response.code()),
                            response.message()
                    );
                    mutableLiveData.setValue(res);
                }
            }

            @Override
            public void onFailure(Call<Result<List<Order>>> call, Throwable t) {
                Result<List<Order>> res = new Result<>(
                        false, "R000",
                        t.getMessage()
                );
                mutableLiveData.setValue(res);
            }
        });
        return mutableLiveData;
    }
}
