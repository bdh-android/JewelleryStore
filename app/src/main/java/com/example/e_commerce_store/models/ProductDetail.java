package com.example.e_commerce_store.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ProductDetail {



    private boolean status;
    private String errorNumber;
    private String msg;
    @SerializedName("product")
    private Product product;
    private String message;

    public ProductDetail(boolean status,String msg, String errorNumber ) {
        this.status = status;
        this.errorNumber = errorNumber;
        this.msg = msg;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Status:"+status);
        stringBuilder.append("\n");
        stringBuilder.append("errorNumber:"+errorNumber);
        stringBuilder.append("\n");
        stringBuilder.append("msg:"+msg);
        stringBuilder.append("\n");
        stringBuilder.append(product.toString());
        if (product.getSizeList()!=null&&!product.getSizeList().isEmpty())
            for (int i=0;i<product.getSizeList().size();i++){
                stringBuilder.append("size"+i+": " +product.getSizeList().get(i).getSize());
                stringBuilder.append("\n");
            }


        return stringBuilder.toString();
    }
}
