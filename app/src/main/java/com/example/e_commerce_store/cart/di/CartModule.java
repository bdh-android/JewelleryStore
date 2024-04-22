package com.example.e_commerce_store.cart.di;

import com.example.e_commerce_store.cart.data.CartRepository;
import com.example.e_commerce_store.cart.data.CartRepositoryImpl;
import com.example.e_commerce_store.cart.data.remote.CartApiInterface;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(ViewModelComponent.class)
public abstract  class CartModule {
    @Provides
    static CartApiInterface provideCartApiInterface(Retrofit retrofit){
        return retrofit.create(CartApiInterface.class);
    }
    @Binds
    abstract CartRepository bindCartRepository(CartRepositoryImpl cartRepository);

}
