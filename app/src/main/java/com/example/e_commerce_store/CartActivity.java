package com.example.e_commerce_store;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.e_commerce_store.adapters.CartAdapter;
import com.example.e_commerce_store.models.Cart;
import com.example.e_commerce_store.models.CartItem;
import com.example.e_commerce_store.models.Order;
import com.example.e_commerce_store.models.OrderItem;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.utils.Util;
import com.example.e_commerce_store.view.RecyclerViewDecoration;
import com.example.e_commerce_store.view.SwipeToDeleteCallback;
import com.example.e_commerce_store.viewmodel.CartViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartActivity extends BaseActivity implements CartAdapter.OnQuantityChangedListener, View.OnClickListener {

    CartViewModel cartViewModel;
    List<CartItem> cartProductList;
    List<CartItem> li;
    CartAdapter cartAdapter;
    RecyclerView recyclerView;
    Button payNow,saveForLater;
    TextView totalPrice,emptyCart;
    int user_id;
    String token;
    double total;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartViewModel= new ViewModelProvider(this).get(CartViewModel.class);
          user_id=SaveToSharedPreferance.getUserId(this);
          token= SaveToSharedPreferance.getToken(this);

          progressBar=findViewById(R.id.progressbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.getIndeterminateDrawable().setTint(getResources().getColor(android.R.color.darker_gray));
        }else {
            progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

         cartProductList = new ArrayList<>();
        Cart cart=SaveToSharedPreferance.getCart(this);
         li = new ArrayList<>();
         if (cart!=null)
         li =cart.getCartItemList();
        constraintLayout=findViewById(R.id.constraintlayout);
        cartAdapter=new CartAdapter(this,this);
         recyclerView=findViewById(R.id.cart_items);
         LinearLayoutManager layoutManager=new LinearLayoutManager(this);
         recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new RecyclerViewDecoration(this,R.drawable.divider));
         recyclerView.setAdapter(cartAdapter);
         payNow=findViewById(R.id.pay_now);
         saveForLater=findViewById(R.id.save_later);
         totalPrice=findViewById(R.id.total_price);
        emptyCart=findViewById(R.id.cart_empty);
         payNow.setOnClickListener(this);
         saveForLater.setOnClickListener(this);

        getCart();

        enableSwipeToDeleteAndUndo();

    }

    private void getCart(){
        cartViewModel.getCart(user_id, Constants.API_KEY,"Bearer "+token).observe(this, new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {

                if (cart != null) {
                    if (cart.isStatus()) {
                        if (cart.getErrorNumber().equals("S000")) {
                            if (cart.getCartItemList() != null && !cart.getCartItemList().isEmpty()) {
                                cartProductList = cart.getCartItemList();

                            }


                        } else if (cart.getErrorNumber().equals("m001")) {
                            if (li.isEmpty())
                                ShowSnackBarMsg(cart.getMessage(), android.R.color.holo_green_dark);

                        }

                        cartProductList = CombineLists(cartProductList, li);
                        if (cartProductList.isEmpty())
                            emptyCart.setVisibility(View.VISIBLE);
                        cartAdapter.setCartItemList(cartProductList);
                        total = calculateTotalPrice(cartProductList);
                        totalPrice.setText(getResources().getString(R.string.price, new DecimalFormat("#.##").format(total)));
                        progressBar.setVisibility(View.INVISIBLE);

                    } else {
                        ShowSnackBarMsg(cart.getMsg(), android.R.color.holo_red_light);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }

        });

    }
    private List<CartItem> CombineLists(List<CartItem> l1,List<CartItem> l2){
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

    //when user change quantity from adapter
    @Override
    public void onQuantityChanged(int position,int Quantity) {
     cartProductList.get(position).setAmount(Quantity);


     total=calculateTotalPrice(cartProductList);

        totalPrice.setText(getResources().getString(R.string.price,new DecimalFormat("#.##").format(total)));

    }

    private double calculateTotalPrice(List<CartItem> list){
        double tot=0;
        for (int i=0;i<list.size();i++){
            double price= Double.parseDouble(list.get(i).getPrice());
            int amount=list.get(i).getAmount();
            double value=price*amount;
            tot=tot+value;
        }

       return tot;
    }
    @Override
    public void onRemoveItem(int position) {
       int s= cartProductList.size();

        cartProductList.remove(position);
        total=calculateTotalPrice(cartProductList);
        if (cartProductList.isEmpty())
            emptyCart.setVisibility(View.VISIBLE);
        else
            emptyCart.setVisibility(View.GONE);
        totalPrice.setText(getResources().getString(R.string.price,new DecimalFormat("#.##").format(total)));
    }

    @Override
    public void onRestoreItem(CartItem item, int position) {
       cartProductList.add(position,item);
        total=calculateTotalPrice(cartProductList);
        if (cartProductList.isEmpty())
            emptyCart.setVisibility(View.VISIBLE);
        else
            emptyCart.setVisibility(View.GONE);
        totalPrice.setText(getResources().getString(R.string.price,new DecimalFormat("#.##").format(total)));
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.pay_now:

                Order order=prepareOrder();
                SaveToSharedPreferance.setOrder(this,order);


                startActivity(new Intent(CartActivity.this,CheckOutActivity.class));
                break;
            case R.id.save_later:
                progressBar.setVisibility(View.VISIBLE);
                JsonObject body=Util.buildCartRequest(user_id, cartProductList);
                cartViewModel.insertCart(Constants.API_KEY,body,"Bearer "+token).observe(this, new Observer<Cart>() {
                    @Override
                    public void onChanged(Cart cart) {
                        if (cart!=null){
                            if (cart.isStatus()) {
                                String s = cart.getMessage();
                                progressBar.setVisibility(View.INVISIBLE);
                                ShowSnackBarMsg(s, android.R.color.holo_green_dark);

                            }else{
                                ShowSnackBarMsg(cart.getMsg(), android.R.color.holo_red_light);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
                break;
        }
    }
    private void enableSwipeToDeleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final CartItem item = cartAdapter.getData().get(position);

                cartAdapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        cartAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onDestroy() {
        SaveToSharedPreferance.deleteCart(this);
        super.onDestroy();
    }

    private Order prepareOrder(){

       List<OrderItem> orderItems=new ArrayList<>();
       for(int i=0;i<cartProductList.size();i++){
           orderItems.add(new OrderItem(cartProductList.get(i).getProduct_id(),cartProductList.get(i).getAmount(),
                   cartProductList.get(i).getSize()));
       }

       Order order=new Order(user_id,total,orderItems);
       return order;
    }
}
