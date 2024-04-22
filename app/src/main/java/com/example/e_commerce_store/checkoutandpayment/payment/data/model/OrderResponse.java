package com.example.e_commerce_store.checkoutandpayment.payment.data.model;

public class OrderResponse {

    private final boolean status;
    private final String errorNumber;
    private final String msg;
    private int order_id;


    public OrderResponse(boolean status, String msg,String errorNumber ) {
        this.status = status;
        this.errorNumber = errorNumber;
        this.msg = msg;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isStatus() {
        return status;
    }

    public String getErrorNumber() {
        return errorNumber;
    }
}
