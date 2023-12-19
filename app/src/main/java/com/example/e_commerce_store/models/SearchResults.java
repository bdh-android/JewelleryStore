package com.example.e_commerce_store.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResults {

    @SerializedName("results")
    List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }
}
