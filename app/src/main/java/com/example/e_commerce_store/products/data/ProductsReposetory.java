package com.example.e_commerce_store.products.data;


import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.products.data.models.Category;
import com.example.e_commerce_store.products.data.models.Feed;
import com.example.e_commerce_store.products.data.models.Product;

import java.util.List;


public interface ProductsReposetory {


      LiveData<Result<List<Category>>> getAllCategories();

      LiveData<Feed> getProductsByCategoryId(int id, int page);

     LiveData<Result<Product>> getProductDetailsById(int id);
    LiveData<Result<List<Product>>> getSearchSuggestions(int cat_id, String searchWord);
    LiveData<Result<List<Product>>> getSearchResults(int cat_id, String searchWord);

    void saveToCart(int prod_id, int i, String size, String name, String price, String image);

}
