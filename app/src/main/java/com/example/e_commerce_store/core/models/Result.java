package com.example.e_commerce_store.core.models;


import com.google.gson.annotations.SerializedName;



public class  Result<T>{
    private boolean status = false;
    @SerializedName("errorNumber")
    private String codeNumber = "";
    @SerializedName("data")
    private T data;
    @SerializedName("msg")
    private String message="";//if cart was empty api send string rather than cart items

    public Result(boolean status, String codeNumber, T data, String message) {
        this.status = status;
        this.codeNumber = codeNumber;

        this.data = data;
        this.message = message;
    }

    public Result(boolean status, String codeNumber, String message) {
        this.status = status;
        this.codeNumber = codeNumber;
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorNumber() {
        return codeNumber;
    }

    public void setErrorNumber(String errorNumber) {
        this.codeNumber = errorNumber;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
