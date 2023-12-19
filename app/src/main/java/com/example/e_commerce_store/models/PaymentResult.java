package com.example.e_commerce_store.models;

public class PaymentResult {

  private  boolean success;

  private  Transaction transaction;

    public boolean isSuccess() {
        return success;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
