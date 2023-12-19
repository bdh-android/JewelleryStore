package com.example.e_commerce_store.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.ProductDetail;
import com.example.e_commerce_store.repository.ProductDetailRepository;

public class ProductDetailsViewModel extends AndroidViewModel {
    private ProductDetailRepository productDetailRepository;
    public ProductDetailsViewModel(@NonNull Application application) {
        super(application);

        productDetailRepository=new ProductDetailRepository(application);
    }

    public LiveData<ProductDetail> getProductDetails(int id,String apiKey,String token){

        return  productDetailRepository.getProductDetails(id,apiKey,token);
    }
}
