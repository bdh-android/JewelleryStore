package com.example.e_commerce_store.models;

import com.google.gson.annotations.SerializedName;

public class PaymentRespone {

    private boolean status;
    private String errorNumber;
    private String msg;
    @SerializedName("client_token")
    private String token;

    public PaymentRespone(boolean status, String msg,  String errorNumber) {
        this.status = status;
        this.errorNumber = errorNumber;
        this.msg = msg;
    }

    public PaymentRespone(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
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
}
