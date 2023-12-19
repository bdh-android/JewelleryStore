package com.example.e_commerce_store.utils;

import com.example.e_commerce_store.models.CartItem;
import com.example.e_commerce_store.models.OrderItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class Util {

    public static JsonObject buildCartRequest(int User_ID, List<CartItem> list){

        JsonArray products=new JsonArray();

        for(int i=0;i<list.size();i++){
            JsonObject cartItem=new JsonObject();

                cartItem.addProperty("product_id",list.get(i).getProduct_id());
                cartItem.addProperty("amount",list.get(i).getAmount());
                cartItem.addProperty("size",list.get(i).getSize());

                products.add(cartItem);


        }

        JsonObject jsonObject=new JsonObject();

            jsonObject.addProperty("user_id",User_ID);

            jsonObject.add("products",products);


        return jsonObject;
    }

    public static JsonObject buildOrderRequest(int User_ID,double totalPrice, List<OrderItem> list){

        JsonArray products=new JsonArray();

        for(int i=0;i<list.size();i++){
            JsonObject orderItem=new JsonObject();

            orderItem.addProperty("product_id",list.get(i).getProduct_id());
            orderItem.addProperty("count",list.get(i).getAmount());
            orderItem.addProperty("size",list.get(i).getSize());

            products.add(orderItem);


        }

        JsonObject jsonObject=new JsonObject();

        jsonObject.addProperty("user_id",User_ID);
        jsonObject.addProperty("total_price",totalPrice);
        jsonObject.add("products",products);


        return jsonObject;
    }
}
