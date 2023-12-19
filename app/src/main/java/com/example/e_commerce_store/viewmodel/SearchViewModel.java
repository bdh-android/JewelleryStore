package com.example.e_commerce_store.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.SearchResults;
import com.example.e_commerce_store.repository.SearchRepository;

public class SearchViewModel extends AndroidViewModel {

    SearchRepository searchRepository;
    public SearchViewModel(@NonNull Application application) {
        super(application);

        searchRepository=new SearchRepository(application);
    }

    public LiveData<SearchResults> getSearchSuggestions(int cat_id,String searchWord,String apiKey,String token){

        return searchRepository.getSuggestions(cat_id, searchWord, apiKey, token);
    }

    public LiveData<SearchResults> getSearchResults(int cat_id,String searchWord,String apiKey,String token){

        return searchRepository.getResults(cat_id, searchWord, apiKey, token);
    }
}
