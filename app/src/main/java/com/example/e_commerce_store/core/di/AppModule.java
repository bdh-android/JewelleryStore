package com.example.e_commerce_store.core.di;

import com.example.e_commerce_store.core.net.ApiKeyInterceptor;
import com.example.e_commerce_store.core.net.TokenInterceptor;
import com.example.e_commerce_store.core.utils.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {


    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient){

        return new Retrofit.Builder()
                .baseUrl(Constants.LOCALHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient( ApiKeyInterceptor apiKeyInterceptor,  TokenInterceptor tokenInterceptor){
        HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor( apiKeyInterceptor)
                .addInterceptor( tokenInterceptor)
                .build();
    }

}
