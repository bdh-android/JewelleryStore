package com.example.e_commerce_store.cart.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce_store.cart.data.CartRepository;
import com.example.e_commerce_store.core.models.CartItem;
import com.example.e_commerce_store.core.models.Result;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CartViewModel extends ViewModel {

    CartRepository cartRepository;

    @Inject
    public CartViewModel(CartRepository cartRepository) {
        this.cartRepository=cartRepository;

    }


    public LiveData<Result<List<CartItem>>> getCart(){

        return cartRepository.getCartItems();
    }

    public LiveData<Result<String>> insertCart(List<CartItem> data){

        return cartRepository.insertCartItems(data);
    }

    public void deleteOfflineCart(){
        cartRepository.deleteOfflineCart();
    }

    public void setOrder(double total, List<CartItem> cartProductList) {
        cartRepository.setOrder(total, cartProductList);
    }
}
