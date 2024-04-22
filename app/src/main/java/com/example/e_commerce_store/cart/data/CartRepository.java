package com.example.e_commerce_store.cart.data;

import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.core.models.CartItem;
import com.example.e_commerce_store.core.models.Result;

import java.util.List;

public interface CartRepository {

     LiveData<Result<List<CartItem>>> getCartItems();
     LiveData<Result<String>> insertCartItems(List<CartItem> data);
     void deleteOfflineCart();

     void setOrder(double total, List<CartItem> cartProductList);
}
