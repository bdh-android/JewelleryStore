package com.example.e_commerce_store.Auth.data.models;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("access_token")
    private String token;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private int tokenExpire;
    private int user_id;

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getTokenExpire() {
        return tokenExpire;
    }

    public int getUser_id() {
        return user_id;
    }
}
