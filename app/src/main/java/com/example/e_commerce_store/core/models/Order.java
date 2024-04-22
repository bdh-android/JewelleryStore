package com.example.e_commerce_store.core.models;

import java.util.List;

public class Order {

    private int id;//order_id
    private int user_id;
    private double total_price;
    private String created_at;
    private List<OrderItem> products;

    public Order(int id, double total_price, String created_at) {
        this.id = id;
        this.total_price = total_price;
        this.created_at = created_at;
    }

    public Order(int user_id, double total_price, List<OrderItem> products) {
        this.user_id = user_id;
        this.total_price = total_price;
        this.products = products;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public List<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItem> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
