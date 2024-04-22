package com.example.e_commerce_store.checkoutandpayment.shippingdetails.di;


import com.example.e_commerce_store.checkoutandpayment.shippingdetails.data.ShippingRepository;
import com.example.e_commerce_store.checkoutandpayment.shippingdetails.data.ShippingRepositoryImpl;
import com.example.e_commerce_store.checkoutandpayment.shippingdetails.data.remote.ShippingApiInterface;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(ViewModelComponent.class)
public abstract class ShippingModule {

    @Provides
    static ShippingApiInterface shippingApiInterface(Retrofit retrofit){
        return retrofit.create(ShippingApiInterface.class);
    }
    @Binds
    abstract ShippingRepository bindShippingRepository(ShippingRepositoryImpl shippingRepository);
}
