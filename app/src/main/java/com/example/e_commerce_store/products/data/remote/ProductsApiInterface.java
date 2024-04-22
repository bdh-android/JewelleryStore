package com.example.e_commerce_store.products.data.remote;

import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.products.data.models.Category;
import com.example.e_commerce_store.products.data.models.Feed;
import com.example.e_commerce_store.products.data.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductsApiInterface {


    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("categories_all?")
    Call<Result<List<Category>>> getAllCategories();

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("all_products_by_category_id/{id}")
    Call<Feed> getProductsByCategoryId(@Path("id") int id, @Query("page") int page);



    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("get_product_by_id/{id}")
    Call<Result<Product>> getProductDetailsById(@Path("id") int id);


    @Headers({"Content-Type:application/json"})
    @GET("get_search_suggestions/{category_id}/{search_word}")
    Call<Result<List<Product>>> getSearchSuggestions(@Path("category_id") int cat_id, @Path("search_word") String searchWord);


    @Headers({"Content-Type:application/json"})
    @GET("get_search_results/{category_id}/{search_word}")
    Call<Result<List<Product>>> getSearchResults(@Path("category_id") int cat_id,@Path("search_word") String searchWord);

}
