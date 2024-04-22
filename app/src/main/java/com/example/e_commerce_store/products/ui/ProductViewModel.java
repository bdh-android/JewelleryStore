package com.example.e_commerce_store.products.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.NetworkState;
import com.example.e_commerce_store.products.data.ProductsReposetory;
import com.example.e_commerce_store.products.data.models.Product;
import com.example.e_commerce_store.products.data.remote.FeedDataFactory;
import com.example.e_commerce_store.products.data.remote.ProductsApiInterface;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductViewModel extends ViewModel {

    //private Executor executor;
    private LiveData<NetworkState> networkState;
    public LiveData<PagedList<Product>> productLiveData;
    ProductsApiInterface productsApiInterface;
    ProductsReposetory productsReposetory;

    @Inject
    public ProductViewModel(ProductsApiInterface productsApiInterface,ProductsReposetory productsReposetory) {
        this.productsApiInterface=productsApiInterface;
        this.productsReposetory=productsReposetory;
    }

    private void init(int id) {
        //executor = Executors.newFixedThreadPool(5);

        FeedDataFactory feedDataFactory = new FeedDataFactory(id,productsApiInterface);

        networkState = Transformations.switchMap(feedDataFactory.getMutableLiveData(),
                dataSource -> dataSource.getNetworkState());

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(5)
                        .setPageSize(10).build();

        if(productLiveData == null)
        productLiveData = (new LivePagedListBuilder(feedDataFactory, pagedListConfig))
                .build();// .setFetchExecutor(executor)
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Product>> getProductsLiveData() {
        return productLiveData;
    }
    public LiveData<Result<List<Product>>> getSearchSuggestions(int cat_id, String searchWord){
        return productsReposetory.getSearchSuggestions(cat_id, searchWord);
    }
    public LiveData<Result<List<Product>>> getSearchResults(int cat_id, String searchWord){
        return productsReposetory.getSearchResults(cat_id, searchWord);
    }

    public void setCategoryId(int id) {
        init(id);
    }

}
