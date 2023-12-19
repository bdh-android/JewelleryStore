package com.example.e_commerce_store.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.models.SearchResults;
import com.example.e_commerce_store.net.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {



    private Application application;

    public SearchRepository(Application application) {
        this.application = application;
    }
// get limit results when user type in search view
    public LiveData<SearchResults> getSuggestions(int cat_id,String searchWord, String apikey, String bearerToken) {
        MutableLiveData<SearchResults> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance().getApi().getSearchSuggestions(cat_id, searchWord, apikey, bearerToken).enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                if (response.isSuccessful() && response != null) {
                    SearchResults res = response.body();
                    mutableLiveData.setValue(res);
                }
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {

            }
        });


        return mutableLiveData;
    }

// get all search results when user submit
    public LiveData<SearchResults> getResults(int cat_id,String searchWord, String apikey, String bearerToken) {
        MutableLiveData<SearchResults> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance().getApi().getSearchResults(cat_id, searchWord, apikey, bearerToken).enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                if (response.isSuccessful() && response != null) {
                    SearchResults res = response.body();
                    mutableLiveData.setValue(res);

                }
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {

            }
        });


        return mutableLiveData;
    }
    }
