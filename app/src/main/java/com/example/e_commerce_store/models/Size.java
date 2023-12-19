package com.example.e_commerce_store.models;

import androidx.annotation.NonNull;

public class Size {


    private int id;
    private int product_id;
    private String size;


    public Size(int id, int product_id, String size) {
        this.id = id;
        this.product_id = product_id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @NonNull
    @Override
    public String toString() {
        return product_id+" "+size;
    }
}
