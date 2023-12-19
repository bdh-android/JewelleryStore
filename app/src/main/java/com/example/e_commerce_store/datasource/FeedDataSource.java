package com.example.e_commerce_store.datasource;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.e_commerce_store.models.Feed;
import com.example.e_commerce_store.models.Product;
import com.example.e_commerce_store.net.APIClient;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedDataSource extends PageKeyedDataSource<Integer, Product> {

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private int id;
    private  String token;
    public FeedDataSource(int id,String token) {
        this.id=id;
        this.token=token;
        networkState=new MutableLiveData();
        initialLoading = new MutableLiveData();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback) {
         initialLoading.postValue(NetworkState.LOADING);
         networkState.postValue(NetworkState.LOADING);
        APIClient.getInstance().getApi().getProductsByCategoryId(id,1, Constants.API_KEY,"Bearer "+token).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if(response.isSuccessful()) {

                    callback.onResult(response.body().getProductList(), null, 2);
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);


                } else {

                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
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

        APIClient.getInstance().getApi().getProductsByCategoryId(id,params.key, Constants.API_KEY,"Bearer "+token).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if(response.isSuccessful()) {
                    int nextKey = (params.key == response.body().getTotal()) ? null : params.key+1;
                    callback.onResult(response.body().getProductList(), nextKey);
                    networkState.postValue(NetworkState.LOADED);

                } else
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));

            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));

            }
        });
    }
}
