package com.example.e_commerce_store.products.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.products.data.models.Category;
import com.example.e_commerce_store.products.data.models.Feed;
import com.example.e_commerce_store.products.data.models.Product;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsRemoteDataSourceImpl implements ProductsRemoteDataSource{
    ProductsApiInterface productsApiInterface;

    @Inject
    public ProductsRemoteDataSourceImpl(ProductsApiInterface productsApiInterface) {
        this.productsApiInterface = productsApiInterface;
    }

    @Override
    public LiveData<Result<List<Category>>> getAllCategories() {
        MutableLiveData<Result<List<Category>>> mutableLiveData = new MutableLiveData<>();
        productsApiInterface.getAllCategories().enqueue(new Callback<Result<List<Category>>>() {
            @Override
            public void onResponse(Call<Result<List<Category>>> call, Response<Result<List<Category>>> response) {
                if (response.isSuccessful() && response != null) {
                    Result<List<Category>> res = response.body();
                    mutableLiveData.setValue(res);
                }else{
                    Result<List<Category>> res=new Result<List<Category>>(
                            false,String.valueOf(response.code()),response.message());
                    mutableLiveData.setValue(res);
                }
            }


            @Override
            public void onFailure(Call<Result<List<Category>>> call, Throwable t) {

                Result<List<Category>> res=new Result<List<Category>>(
                        false,"R000",t.getMessage());
                mutableLiveData.setValue(res);
            }
        });

        return mutableLiveData;
    }

    @Override
    public LiveData<Feed> getProductsByCategoryId(int id, int page) {
        return null;
    }

    @Override
    public LiveData<Result<Product>> getProductDetailsById(int id) {
        MutableLiveData<Result<Product>> mutableLiveData = new MutableLiveData<>();
        productsApiInterface.getProductDetailsById(id).enqueue(new Callback<Result<Product>>() {
            @Override
            public void onResponse(Call<Result<Product>> call, Response<Result<Product>> response) {
                if (response.isSuccessful() && response != null) {
                    Result<Product> res = response.body();
                    mutableLiveData.setValue(res);


                }else{
                    Result<Product> res=new Result<Product>(
                            false,String.valueOf(response.code()),response.message());
                    mutableLiveData.setValue(res);

                }
            }


            @Override
            public void onFailure(Call<Result<Product>> call, Throwable t) {

                Result<Product> res=new Result<Product>(
                        false,"R000",t.getMessage());
                mutableLiveData.setValue(res);
            }
        });

        return mutableLiveData;
    }

    @Override
    public LiveData<Result<List<Product>>> getSearchSuggestions(int cat_id, String searchWord) {
        MutableLiveData<Result<List<Product>>> mutableLiveData=new MutableLiveData<>();
        productsApiInterface.getSearchSuggestions(cat_id, searchWord).enqueue(new Callback<Result<List<Product>>>() {
            @Override
            public void onResponse(Call<Result<List<Product>>> call, Response<Result<List<Product>>> response) {
                if (response.isSuccessful() && response != null) {
                    Result<List<Product>> res = response.body();
                    mutableLiveData.setValue(res);

                }else{
                    Result<List<Product>> res=new Result<List<Product>>(false,String.valueOf(response.code()),response.message());
                    mutableLiveData.setValue(res);
                }
            }

            @Override
            public void onFailure(Call<Result<List<Product>>> call, Throwable t) {
                Result<List<Product>> res=new Result<>(false,"R000",t.getMessage());
                mutableLiveData.setValue(res);
            }
        });


        return mutableLiveData;
    }

    @Override
    public LiveData<Result<List<Product>>> getSearchResults(int cat_id, String searchWord) {
        MutableLiveData<Result<List<Product>>> mutableLiveData=new MutableLiveData<>();
        productsApiInterface.getSearchResults(cat_id, searchWord).enqueue(new Callback<Result<List<Product>>>() {
            @Override
            public void onResponse(Call<Result<List<Product>>> call, Response<Result<List<Product>>> response) {
                if (response.isSuccessful() && response != null) {
                    Result<List<Product>> res = response.body();
                    mutableLiveData.setValue(res);
                }else{
                    Result<List<Product>> res=new Result<List<Product>>(false,String.valueOf(response.code()),response.message());

                    mutableLiveData.setValue(res);
                }
            }

            @Override
            public void onFailure(Call<Result<List<Product>>> call, Throwable t) {
                Result<List<Product>> res=new Result<>(false,"R000",t.getMessage());
                mutableLiveData.setValue(res);
            }
        });


        return mutableLiveData;
    }
}
