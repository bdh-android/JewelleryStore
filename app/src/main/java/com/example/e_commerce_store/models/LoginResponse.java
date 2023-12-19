package com.example.e_commerce_store.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private boolean status;
    private String errorNumber;
    private String msg;
    @SerializedName("data")
    private Token token;
    private String error;

    public LoginResponse(boolean status, String msg, String errorNumber ) {
        this.status = status;
        this.errorNumber = errorNumber;
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public String getErrorNumber() {
        return errorNumber;
    }

    public String getMsg() {
        return msg;
    }

    public Token getToken() {
        return token;
    }


}
