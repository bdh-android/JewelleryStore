package com.example.e_commerce_store.products.data;


import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.core.models.CartItem;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.SharedPrefs;
import com.example.e_commerce_store.products.data.models.Category;
import com.example.e_commerce_store.products.data.models.Feed;
import com.example.e_commerce_store.products.data.models.Product;
import com.example.e_commerce_store.products.data.remote.ProductsRemoteDataSource;

import java.util.List;

import javax.inject.Inject;



public class ProductsReposetoryImpl implements ProductsReposetory{
    ProductsRemoteDataSource productsRemoteDataSource;
    SharedPrefs sharedPrefs;

    @Inject
    public ProductsReposetoryImpl(ProductsRemoteDataSource productsRemoteDataSource,SharedPrefs sharedPrefs) {
        this.productsRemoteDataSource = productsRemoteDataSource;
        this.sharedPrefs=sharedPrefs;
    }

    @Override
    public LiveData<Result<List<Category>>> getAllCategories() {
        return productsRemoteDataSource.getAllCategories();
    }

    @Override
    public LiveData<Feed> getProductsByCategoryId(int id, int page) {
        return null;
    }

    @Override
    public LiveData<Result<Product>> getProductDetailsById(int id) {
        return productsRemoteDataSource.getProductDetailsById(id);

    }

    @Override
    public LiveData<Result<List<Product>>> getSearchSuggestions(int cat_id, String searchWord) {
        return productsRemoteDataSource.getSearchSuggestions(cat_id, searchWord);
    }

    @Override
    public LiveData<Result<List<Product>>> getSearchResults(int cat_id, String searchWord) {
        return productsRemoteDataSource.getSearchResults(cat_id, searchWord);
    }

    @Override
    public void saveToCart(int prod_id, int i, String size, String name, String price, String image) {
        int userID = sharedPrefs.getUserId();
        sharedPrefs.saveToCart( new CartItem(userID, prod_id, 1, size, name, price, image));
    }

}
