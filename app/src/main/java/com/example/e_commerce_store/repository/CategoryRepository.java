package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.CategoryResponse;
import com.example.e_commerce_store.net.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {


    private Application application;

    public CategoryRepository(Application application) {
        this.application = application;
    }

    public LiveData<CategoryResponse> getAllCategories(String apikey, String bearerToken) {

        MutableLiveData<CategoryResponse> mutableLiveData = new MutableLiveData<>();
        APIClient.getInstance().getApi().getAllCategories(apikey, bearerToken).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response != null) {
                    CategoryResponse res = response.body();
                    mutableLiveData.setValue(res);


                }else{

                    CategoryResponse res=new CategoryResponse(false,response.message(),String.valueOf(response.code()));
                    mutableLiveData.setValue(res);
                }
                }


            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

                CategoryResponse res=new CategoryResponse(false,t.getMessage(),"R000");
                mutableLiveData.setValue(res);
            }
        });

        return mutableLiveData;
    }
}