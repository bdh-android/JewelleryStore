package com.example.e_commerce_store.products.data.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.e_commerce_store.core.utils.NetworkState;
import com.example.e_commerce_store.products.data.models.Feed;
import com.example.e_commerce_store.products.data.models.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedDataSource extends PageKeyedDataSource<Integer, Product> {

    private final ProductsApiInterface productsApiInterfac;
    private final MutableLiveData networkState;

    private final int id;

    public FeedDataSource(int id,ProductsApiInterface productsApiInterfac) {
        this.id=id;
        this.productsApiInterfac= productsApiInterfac;
        networkState=new MutableLiveData();

    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }



    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback) {

        networkState.postValue(NetworkState.LOADING);
        productsApiInterfac.getProductsByCategoryId(id,1).enqueue(new Callback<Feed>(){
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if(response.isSuccessful()) {

                    callback.onResult(response.body().getProductList(), null, 2);

                    networkState.postValue(NetworkState.LOADED);


                } else {


                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {

                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        networkState.postValue(NetworkState.LOADING);

        productsApiInterfac.getProductsByCategoryId(id,params.key).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if(response.isSuccessful()) {
                    int nextKey = (params.key == response.body().getTotal()) ? null : params.key+1;
                    callback.onResult(response.body().getProductList(), nextKey);
                    networkState.postValue(NetworkState.LOADED);

                } else networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));

            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));

            }
        });
    }
}
