package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.ProductDetail;
import com.example.e_commerce_store.net.APIClient;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailRepository {


    private Application application;

    public ProductDetailRepository(Application application) {
        this.application = application;
    }

    public LiveData<ProductDetail> getProductDetails(int id,String apikey, String bearerToken) {

        MutableLiveData<ProductDetail> mutableLiveData = new MutableLiveData<>();
        APIClient.getInstance().getApi().getProductDetailsById(id,apikey, bearerToken).enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.isSuccessful() && response != null) {
                    ProductDetail res = response.body();
                    mutableLiveData.setValue(res);


                }else{
                    ProductDetail res=new ProductDetail(false,response.message(),String.valueOf(response.code()));
                    mutableLiveData.setValue(res);

                }
            }


            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

                ProductDetail res=new ProductDetail(false,t.getMessage(),"R000");
                mutableLiveData.setValue(res);
            }
        });

        return mutableLiveData;
    }
}
