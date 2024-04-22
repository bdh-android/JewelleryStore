package com.example.e_commerce_store.cart.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.cart.data.remote.CartApiInterface;
import com.example.e_commerce_store.core.models.CartItem;
import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.OrderItem;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.SharedPrefs;
import com.example.e_commerce_store.core.utils.Util;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepositoryImpl implements CartRepository {
    private final SharedPrefs sharedPrefs;
    private final CartApiInterface cartApiInterface;

    @Inject
    public CartRepositoryImpl(SharedPrefs sharedPrefs, CartApiInterface cartApiInterface) {
        this.sharedPrefs = sharedPrefs;
        this.cartApiInterface = cartApiInterface;
    }


    @Override
    public LiveData<Result<List<CartItem>>> getCartItems(){
        int userId = sharedPrefs.getUserId();
        MutableLiveData<Result<List<CartItem>>> mutableLiveData=new MutableLiveData<>();
        cartApiInterface.getCartItems(userId).enqueue(new Callback<Result<List<CartItem>>>() {
            @Override
            public void onResponse(Call<Result<List<CartItem>>> call, Response<Result<List<CartItem>>> response) {
                if (response.isSuccessful() && response != null) {
                    Result<List<CartItem>> res = response.body();
                    res = makeCart(res);
                    if(!res.getData().isEmpty())
                        res.setErrorNumber("S000");
                    mutableLiveData.setValue(res);
                }else{
                    Result<List<CartItem>> res =new Result<List<CartItem>>(false,String.valueOf(response.code()),response.message());
                    mutableLiveData.setValue(res);

                }
            }

            @Override
            public void onFailure(Call<Result<List<CartItem>>> call, Throwable t) {

                Result<List<CartItem>> res =new Result<List<CartItem>>(false,"R000",t.getMessage());
                mutableLiveData.setValue(res);
            }
        });
        return mutableLiveData;
    }

    @Override
    public LiveData<Result<String>> insertCartItems(List<CartItem> data){
        MutableLiveData<Result<String>> mutableLiveData=new MutableLiveData<>();
        if (data.isEmpty()) {
            Result<String> res = new Result<>(
                    false, "R000", "There are no data! please add some products first");
            mutableLiveData.setValue(res);
            return mutableLiveData;
        }
        JsonObject body= Util.buildCartRequest(sharedPrefs.getUserId(), data);


        cartApiInterface.insertItemsIntoCart(body).enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                if (response.isSuccessful() && response != null) {
                    Result<String> res = response.body();
                    mutableLiveData.setValue(res);
                }else{
                    Result<String> res =new Result<>(
                            false,String.valueOf(response.code()),response.message());
                    mutableLiveData.setValue(res);
                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {

                Result<String> res =new Result<>(
                        false,"R000",t.getMessage());
                mutableLiveData.setValue(res);
            }
        });
        return mutableLiveData;
    }
    @Override
    public void deleteOfflineCart(){
        sharedPrefs.deleteCart();
    }

    @Override
    public void setOrder(double total, List<CartItem> cartProductList) {
        Order order =prepareOrder( sharedPrefs.getUserId(),total,  cartProductList);
        sharedPrefs.setOrder(order);
    }

    Result<List<CartItem>> makeCart(Result<List<CartItem>> cart){
        List<CartItem> cartofflineProductList =sharedPrefs.getCart().getCartItemList();
        List<CartItem> cartProductList = cart!=null? cart.getData(): null;

        cartProductList = CombineLists(cartProductList, cartofflineProductList);
        if (cart!=null)
            cart.setData(cartProductList);
        return cart;

    }
    private List<CartItem> CombineLists(List<CartItem> l1, List<CartItem> l2){
        if ((l1==null||l1.isEmpty())&&(l2==null||l2.isEmpty()))
            return l1;
        else if (l1==null||l1.isEmpty()){
            l1=l2;
            return l1;}
        else if (l2==null||l2.isEmpty())
            return l1;
        else {
            Iterator<CartItem> iterator=l2.iterator();
            while (iterator.hasNext()) {
                boolean shuoldAddNew=true;
                CartItem cartItem = iterator.next();
                for (int i1=0;i1<l1.size();i1++){
                    if (cartItem.equals(l1.get(i1))){
                        int amount1=l1.get(i1).getAmount();
                        int amount2=cartItem.getAmount();
                        l1.get(i1).setAmount(amount1+amount2);

                        iterator.remove();
                        shuoldAddNew=false;
                        break;
                    }
                }
                if (shuoldAddNew){
                    l1.add(cartItem);
                    iterator.remove();
                }
            }



            return l1;
        }

    }

    private Order prepareOrder(int user_id,double total,List<CartItem> cartProductList){

        List<OrderItem> orderItems=new ArrayList<>();
        for(int i=0;i<cartProductList.size();i++){
            orderItems.add(new OrderItem(cartProductList.get(i).getProduct_id(),cartProductList.get(i).getAmount(),
                    cartProductList.get(i).getSize()));
        }

        Order order=new Order(user_id,total,orderItems);
        return order;
    }
}
