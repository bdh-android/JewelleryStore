package com.example.e_commerce_store.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.e_commerce_store.models.Cart;
import com.example.e_commerce_store.models.CartItem;
import com.example.e_commerce_store.models.Order;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SaveToSharedPreferance {

    private static SharedPreferences getPrefs(Context context){

        return PreferenceManager.getDefaultSharedPreferences(context);

    }
    public static void setUserEmail(Context context,String email){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString(Constants.USER_EMAIL,email);
        editor.apply();
    }

    public static String getUserEmail(Context context){

        return getPrefs(context).getString(Constants.USER_EMAIL,"");
    }

    public static void setUserName(Context context,String name){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString(Constants.USER_NAME,name);
        editor.apply();
    }

    public static String getUserName(Context context){

        return getPrefs(context).getString(Constants.USER_NAME,"");
    }

    public static void setUserId(Context context,int id){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putInt(Constants.USER_ID,id);
        editor.apply();
    }

    public static int getUserId(Context context){

        return getPrefs(context).getInt(Constants.USER_ID,0);
    }

    public static void setToken(Context context,String token){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString(Constants.ACCESS_TOKEN,token);
        editor.apply();
    }

    public static String getToken(Context context){

        return getPrefs(context).getString(Constants.ACCESS_TOKEN,"");
    }

    public static void setIsLogin(Context context,boolean islogin){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putBoolean(Constants.ACCESS_TOKEN,islogin);
        editor.apply();

    }

    public static boolean getIsLogin(Context context){
        return getPrefs(context).getBoolean(Constants.ISLOGIN,false);
    }

    public static void deleteCart(Context context){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.remove(Constants.CART);
        editor.apply();
    }
    public static Cart getCart(Context context){
        Gson gson=new Gson();
        String c=getPrefs(context).getString(Constants.CART,"");
        Cart cart=gson.fromJson(c,Cart.class);

        return cart;
    }
    public static void saveToCart(Context context , CartItem cartItem){
     Gson gson=new Gson();

        boolean shouldAddNew=true;
         Cart cart=getCart(context);
        List<CartItem> list=new ArrayList<>();
         if (cart!=null&&!cart.getCartItemList().isEmpty()){
              list= cart.getCartItemList();


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
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString(Constants.CART,c);
        editor.apply();
    }

    public static void setOrder(Context context,Order order){
        Gson gson=new Gson();
        String o=gson.toJson(order);
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString(Constants.Order,o);
        editor.apply();
    }

    public static Order getOrder(Context context){
        Gson gson=new Gson();
        String c=getPrefs(context).getString(Constants.Order,"");
        Order order=gson.fromJson(c,Order.class);

        return order;
    }

    public static void deleteOrder(Context context){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.remove(Constants.Order);
        editor.apply();
    }
}
