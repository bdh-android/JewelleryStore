package com.example.e_commerce_store.products.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.products.data.ProductsReposetory;
import com.example.e_commerce_store.products.data.models.Product;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductDetailsViewModel extends ViewModel {
    // private ProductDetailRepository productDetailRepository;
    private final ProductsReposetory productsReposetory;
    @Inject
    public ProductDetailsViewModel(ProductsReposetory productsReposetory) {
        this.productsReposetory=productsReposetory;
    }

    public LiveData<Result<Product>> getProductDetails(int id){

        return  productsReposetory.getProductDetailsById(id);
    }

    public void saveToCart(int prod_id, int i, String size, String name, String price, String image) {
        productsReposetory.saveToCart(prod_id,i,size,name,price,image);
    }
}
