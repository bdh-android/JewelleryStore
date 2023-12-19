package com.example.e_commerce_store.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

    private boolean status;
    private String errorNumber;
    private String msg;
    @SerializedName("data")
    private List<Category> categoryList;

    public CategoryResponse(boolean status, String msg, String errorNumber) {
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

    public List<Category> getCategoryList() {
        return categoryList;
    }
}
