package com.example.e_commerce_store.models;

import androidx.annotation.Nullable;

public class CartItem {


    private int user_id;
    private int product_id;
    private int amount;
    private String size;
    private String name;
    private String price;
    private String image;

    public CartItem(int user_id, int product_id, int amount,String size) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.amount = amount;
        this.size=size;
    }

    public CartItem(int user_id, int product_id, int amount, String size, String name, String price, String image) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.amount = amount;
        this.size = size;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj==this)
        return true;
        if (!(obj instanceof CartItem)){
            return false;
        }
        CartItem cartItem=(CartItem)obj;
        return product_id == cartItem.product_id
                &&size.equals(cartItem.size);
    }
}
