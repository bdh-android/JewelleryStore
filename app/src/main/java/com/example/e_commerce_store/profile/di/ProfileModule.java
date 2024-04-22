package com.example.e_commerce_store.profile.di;


import com.example.e_commerce_store.profile.data.ProfileRepository;
import com.example.e_commerce_store.profile.data.ProfileRepositoryImpl;
import com.example.e_commerce_store.profile.data.remote.ProfileApiInterface;
import com.example.e_commerce_store.profile.data.remote.ProfileRemoteDataSource;
import com.example.e_commerce_store.profile.data.remote.ProfileRemoteDataSourceImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;
import retrofit2.Retrofit;

@Module
@InstallIn(ViewModelComponent.class)
public abstract class ProfileModule {
    @ViewModelScoped
    @Provides
    static ProfileApiInterface provideProfileApiInterface(Retrofit retrofit){
        return retrofit.create(ProfileApiInterface.class);
    }
    @ViewModelScoped
    @Binds
    abstract ProfileRemoteDataSource bindProfileRemoteDataSource(ProfileRemoteDataSourceImpl authRemoteDataSource);
    @ViewModelScoped
    @Binds
    abstract ProfileRepository bindProfileRepository(ProfileRepositoryImpl authRepository);

}
