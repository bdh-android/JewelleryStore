package com.example.e_commerce_store.products.data.remote;

import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.products.data.models.Category;
import com.example.e_commerce_store.products.data.models.Feed;
import com.example.e_commerce_store.products.data.models.Product;

import java.util.List;


public interface ProductsRemoteDataSource {

    LiveData<Result<List<Category>>> getAllCategories();
    LiveData<Feed> getProductsByCategoryId(int id, int page);
    LiveData<Result<Product>> getProductDetailsById(int id);
    LiveData<Result<List<Product>>> getSearchSuggestions(int cat_id, String searchWord);
    LiveData<Result<List<Product>>> getSearchResults(int cat_id, String searchWord);
}
