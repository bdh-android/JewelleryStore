package com.example.e_commerce_store.core.net;

import android.content.Context;

import com.example.e_commerce_store.core.di.AuthTokenInterceptor;
import com.example.e_commerce_store.core.utils.SharedPrefs;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@AuthTokenInterceptor
public class TokenInterceptor implements Interceptor {

    Context appContext;
    SharedPrefs sharedPrefs;
    @Inject
    public TokenInterceptor(@ApplicationContext Context appContext,SharedPrefs sharedPrefs) {
        this.appContext=appContext;
        this.sharedPrefs=sharedPrefs;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (request.header("No-Authentication")==null){
            String token = sharedPrefs.getToken();
            if (!token.isEmpty()){
                token = "Bearer "+token;
                request =request.newBuilder().addHeader("Authorization",token).build();
            }
        }
        return chain.proceed(request);
    }
}
