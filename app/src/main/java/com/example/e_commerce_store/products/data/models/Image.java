package com.example.e_commerce_store.products.data.models;

import androidx.annotation.NonNull;

public class Image {

    private int id;
    private int product_id;
    private String image;

    public Image(int id, int product_id, String image) {
        this.id = id;
        this.product_id = product_id;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
          return product_id+" "+image;
    }
}
