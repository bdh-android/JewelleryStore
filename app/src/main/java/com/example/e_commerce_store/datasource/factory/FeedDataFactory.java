package com.example.e_commerce_store.datasource.factory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.e_commerce_store.datasource.FeedDataSource;

public class FeedDataFactory extends DataSource.Factory {
    private MutableLiveData<FeedDataSource> mutableLiveData;
    private FeedDataSource feedDataSource;
    private int id;
    private String token;


    public FeedDataFactory(int id,String token) {
        this.id=id;
        this.token=token;
        this.mutableLiveData = new MutableLiveData<FeedDataSource>();
    }

    @Override
    public DataSource create() {
        feedDataSource = new FeedDataSource(id, token);
        mutableLiveData.postValue(feedDataSource);
        return feedDataSource;
    }


    public MutableLiveData<FeedDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
