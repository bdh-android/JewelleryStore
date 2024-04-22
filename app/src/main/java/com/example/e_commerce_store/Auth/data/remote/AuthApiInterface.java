package com.example.e_commerce_store.Auth.data.remote;

import com.example.e_commerce_store.Auth.data.models.Token;
import com.example.e_commerce_store.Auth.data.models.User;
import com.example.e_commerce_store.core.models.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthApiInterface {

    @FormUrlEncoded
    @Headers({"No-Authentication:true"})
    @POST("auth/registernewuser")
    Call<Result<User>> createNewUser(@Field("name") String name,
                                         @Field("email") String email,
                                         @Field("password") String password);


    @FormUrlEncoded
    @Headers({"No-Authentication:true"})
    @POST("auth/login")
    Call<Result<Token>> loginUser(@Field("email") String email,
                                  @Field("password") String password);


    @Headers({"Content-Type:application/json;charset=UTF-8;"})
    @POST("auth/me")
    Call<Result<User>> getUser();

}
