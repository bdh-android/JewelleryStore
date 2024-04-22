package com.example.e_commerce_store.products.di;

import com.example.e_commerce_store.core.utils.SharedPrefs;
import com.example.e_commerce_store.products.data.ProductsReposetory;
import com.example.e_commerce_store.products.data.ProductsReposetoryImpl;
import com.example.e_commerce_store.products.data.remote.ProductsApiInterface;
import com.example.e_commerce_store.products.data.remote.ProductsRemoteDataSource;
import com.example.e_commerce_store.products.data.remote.ProductsRemoteDataSourceImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(ViewModelComponent.class)
public class ProductModule {

    @Provides
    ProductsApiInterface provideProductsApiInterface(Retrofit retrofit){
        return retrofit.create(ProductsApiInterface.class);
    }

    @Provides
    ProductsReposetory provideProductsReposetory( ProductsRemoteDataSource productsRemoteDataSource, SharedPrefs sharedPrefs){
        return new ProductsReposetoryImpl(productsRemoteDataSource,sharedPrefs);
    }
    @Provides
    ProductsRemoteDataSource provideProductsRemoteDataSource(ProductsApiInterface productsApiInterface){
        return new ProductsRemoteDataSourceImpl(productsApiInterface);
    }

}
