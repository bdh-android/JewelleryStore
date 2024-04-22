package com.example.e_commerce_store.core.net;

import com.example.e_commerce_store.core.di.KeyInterceptor;
import com.example.e_commerce_store.core.utils.Constants;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@KeyInterceptor
public class ApiKeyInterceptor implements Interceptor {
    @Inject
    public ApiKeyInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request =request.newBuilder().addHeader("api_password",Constants.API_KEY).build();
        return chain.proceed(request);
    }
}
