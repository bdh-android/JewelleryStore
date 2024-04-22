package com.example.e_commerce_store.core.models;

public class OrderItem {

    private int product_id;
    private int count;
    private String size;

    public OrderItem(int product_id, int amount, String size) {
        this.product_id = product_id;
        this.count = amount;
        this.size = size;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getAmount() {
        return count;
    }

    public void setAmount(int amount) {
        this.count = amount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
