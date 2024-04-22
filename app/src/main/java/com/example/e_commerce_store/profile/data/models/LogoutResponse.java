package com.example.e_commerce_store.profile.data.models;


import com.google.gson.annotations.SerializedName;

public class LogoutResponse {

    private final boolean status;
    private final String errorNumber;
    private final String msg;
    @SerializedName("data")
    private String data;

    public LogoutResponse(boolean status, String msg, String errorNumber ) {
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




}
