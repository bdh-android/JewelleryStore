package com.example.e_commerce_store.products.data.remote;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import javax.inject.Inject;

public class FeedDataFactory extends DataSource.Factory {
    private final MutableLiveData<FeedDataSource> mutableLiveData;
    private FeedDataSource feedDataSource;
    private final int id;
    ProductsApiInterface productsApiInterfac;


    @Inject
    public FeedDataFactory(int id, ProductsApiInterface productsApiInterfac) {
        this.id=id;
        this.productsApiInterfac=productsApiInterfac;
        this.mutableLiveData = new MutableLiveData<FeedDataSource>();
    }

    @Override
    public DataSource create() {
        feedDataSource = new FeedDataSource(id,productsApiInterfac);
        mutableLiveData.postValue(feedDataSource);
        return feedDataSource;
    }


    public MutableLiveData<FeedDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
