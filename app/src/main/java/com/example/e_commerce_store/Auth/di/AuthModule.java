package com.example.e_commerce_store.Auth.di;

import com.example.e_commerce_store.Auth.data.AuthRepository;
import com.example.e_commerce_store.Auth.data.AuthRepositoryImpl;
import com.example.e_commerce_store.Auth.data.remote.AuthApiInterface;
import com.example.e_commerce_store.Auth.data.remote.AuthRemoteDataSource;
import com.example.e_commerce_store.Auth.data.remote.AuthRemoteDataSourceImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;
import retrofit2.Retrofit;

@Module
@InstallIn(ViewModelComponent.class)
public abstract class AuthModule {

    @ViewModelScoped
    @Provides
    static AuthApiInterface provideAuthApiInterface(Retrofit retrofit){
        return retrofit.create(AuthApiInterface.class);
    }
    @ViewModelScoped
    @Binds
   abstract AuthRemoteDataSource bindAuthRemoteDataSource(AuthRemoteDataSourceImpl authRemoteDataSource);
    @ViewModelScoped
    @Binds
    abstract AuthRepository bindAuthRepository(AuthRepositoryImpl authRepository);
}
