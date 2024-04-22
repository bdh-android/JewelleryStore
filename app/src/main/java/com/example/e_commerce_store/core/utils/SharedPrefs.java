package com.example.e_commerce_store.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.e_commerce_store.cart.data.model.Cart;
import com.example.e_commerce_store.core.models.Address;
import com.example.e_commerce_store.core.models.CartItem;
import com.example.e_commerce_store.core.models.Order;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
@Singleton
public class SharedPrefs {

    Context context;

    @Inject
    public SharedPrefs(@ApplicationContext Context context) {
        this.context = context;
    }

    private  SharedPreferences getPrefs(){

        return PreferenceManager.getDefaultSharedPreferences(context);

    }
    public  void setUserEmail(String email){
        SharedPreferences.Editor editor=getPrefs().edit();
        editor.putString(Constants.USER_EMAIL,email);
        editor.apply();
    }

    public  String getUserEmail(){

        return getPrefs().getString(Constants.USER_EMAIL,"");
    }

    public  void setUserName(String name){
        SharedPreferences.Editor editor=getPrefs().edit();
        editor.putString(Constants.USER_NAME,name);
        editor.apply();
    }

    public   String getUserName(){

        return getPrefs().getString(Constants.USER_NAME,"");
    }

    public   void setUserId(int id){
        SharedPreferences.Editor editor=getPrefs().edit();
        editor.putInt(Constants.USER_ID,id);
        editor.apply();
    }

    public   int getUserId(){

        return getPrefs().getInt(Constants.USER_ID,0);
    }

    public   void setToken(String token){
        SharedPreferences.Editor editor=getPrefs().edit();
        editor.putString(Constants.ACCESS_TOKEN,token);
        editor.apply();
    }

    public  String getToken(){

        return getPrefs().getString(Constants.ACCESS_TOKEN,"");
    }

    public  void setIsLogin(boolean islogin){
        SharedPreferences.Editor editor=getPrefs().edit();
        editor.putBoolean(Constants.ACCESS_TOKEN,islogin);
        editor.apply();

    }

    public  boolean getIsLogin(){
        return getPrefs().getBoolean(Constants.ISLOGIN,false);
    }

    public  void deleteCart(){
        SharedPreferences.Editor editor=getPrefs().edit();
        editor.remove(Constants.CART);
        editor.apply();
    }
    public  Cart getCart(){
        Gson gson=new Gson();
        String c=getPrefs().getString(Constants.CART,"");
        Cart cart=gson.fromJson(c,Cart.class);

        return cart!=null? cart:new Cart();
    }
    public  void saveToCart(CartItem cartItem){
        Gson gson=new Gson();

        boolean shouldAddNew=true;
        Cart cart=getCart();
        List<CartItem> list=cart.getCartItemList()==null ? new ArrayList<>() :cart.getCartItemList();
        if (list!=null&&!list.isEmpty()){
            for (int i=0;i<list.size();i++){
                if (cartItem.equals(list.get(i))){
                    int amount=list.get(i).getAmount();
                    list.get(i).setAmount(amount+1);
                    shouldAddNew=false;
                    break;
                }

            }

            if (shouldAddNew)
                list.add(cartItem);



        }else{
            cart=new Cart();
            list.add(cartItem);
        }

        cart.setCartItemList(list);
        String c=gson.toJson(cart);
        SharedPreferences.Editor editor=getPrefs().edit();
        editor.putString(Constants.CART,c);
        editor.apply();
    }

    public  void setOrder( Order order){
        Gson gson=new Gson();
        String o=gson.toJson(order);
        SharedPreferences.Editor editor=getPrefs().edit();
        editor.putString(Constants.ORDER,o);
        editor.apply();
    }

    public Order getOrder(){
        Gson gson=new Gson();
        String c=getPrefs().getString(Constants.ORDER,"");
        Order order=gson.fromJson(c,Order.class);
        return order;
    }

    public void deleteOrder(){
        SharedPreferences.Editor editor=getPrefs().edit();
        editor.remove(Constants.ORDER);
        editor.apply();
    }
    public void saveShippingDetails(Address shippingDetails){
        Gson gson=new Gson();
        String o=gson.toJson(shippingDetails);
        SharedPreferences.Editor editor=getPrefs().edit();
        editor.putString(Constants.SHIPPINGADDRESS,o);
        editor.apply();
    }
    public Address getShippingDetails(){
        Gson gson=new Gson();
        String c=getPrefs().getString(Constants.SHIPPINGADDRESS,"");
        Address shippingDetails=gson.fromJson(c,Address.class);
        return shippingDetails;
    }
}
