package com.example.e_commerce_store.models;

import com.google.gson.annotations.SerializedName;

public class PasswordResponse {

    private boolean status;
    private String errorNumber;
    private String msg;
    @SerializedName("message")
    private String message;
    private String error;

    public PasswordResponse(boolean status, String msg, String errorNumber) {
        this.status = status;
        this.errorNumber = errorNumber;
        this.msg = msg;
    }

    public PasswordResponse(boolean status, String errorNumber, String msg, String message, String error) {
        this.status = status;
        this.errorNumber = errorNumber;
        this.msg = msg;
        this.message = message;
        this.error = error;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(String errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
