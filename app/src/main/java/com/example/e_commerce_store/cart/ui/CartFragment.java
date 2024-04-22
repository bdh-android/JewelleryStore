package com.example.e_commerce_store.cart.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_store.MainActivity;
import com.example.e_commerce_store.R;
import com.example.e_commerce_store.core.models.CartItem;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.Constants;
import com.example.e_commerce_store.core.view.RecyclerViewDecoration;
import com.example.e_commerce_store.core.view.SwipeToDeleteCallback;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends Fragment implements CartAdapter.OnQuantityChangedListener, View.OnClickListener {

    private CartViewModel cartViewModel;
    private List<CartItem> cartProductList;
    private CartAdapter cartAdapter;
    private RecyclerView recyclerView;
    private Button payNow,saveForLater;
    private TextView totalPrice,emptyCart;
    private double total;
    private ProgressBar progressBar;
    private ConstraintLayout constraintLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartViewModel= new ViewModelProvider(this).get(CartViewModel.class);
        progressBar=view.findViewById(R.id.progressbar);
        cartProductList = new ArrayList<>();
        constraintLayout=view.findViewById(R.id.constraintlayout);
        cartAdapter=new CartAdapter(this.getActivity(),this);
        recyclerView=view.findViewById(R.id.cart_items);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new RecyclerViewDecoration(getActivity(),R.drawable.divider));
        recyclerView.setAdapter(cartAdapter);
        payNow=view.findViewById(R.id.pay_now);
        saveForLater=view.findViewById(R.id.save_later);
        totalPrice=view.findViewById(R.id.total_price);
        emptyCart=view.findViewById(R.id.cart_empty);
        payNow.setOnClickListener(this);
        saveForLater.setOnClickListener(this);

        getCart();

        enableSwipeToDeleteAndUndo();
    }


    private void getCart(){
        cartViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<Result<List<CartItem>>>() {
            @Override
            public void onChanged(Result<List<CartItem>> cart) {

                if (cart != null) {
                    if (cart.getStatus()) {
                        if (cart.getErrorNumber().equals(Constants.SUCCESS_CODE_API)) {
                            if (cart.getData() != null && !cart.getData().isEmpty()) {
                                cartProductList = cart.getData();
                            }

                        } else if (cart.getErrorNumber().equals(Constants.RESPONSE_CART_IS_EMPTY)) {
                            ((MainActivity)requireActivity()).ShowSnackBarMsg(cart.getMessage(), android.R.color.holo_green_dark);
                        }


                        if (cartProductList.isEmpty())
                            emptyCart.setVisibility(View.VISIBLE);
                        cartAdapter.setCartItemList(cartProductList);
                        total = calculateTotalPrice(cartProductList);
                        totalPrice.setText(getResources().getString(R.string.price, new DecimalFormat("#.##").format(total)));
                        progressBar.setVisibility(View.INVISIBLE);

                    } else {
                        ((MainActivity) requireActivity()).ShowSnackBarMsg(cart.getMessage(), android.R.color.holo_red_light);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }

        });

    }
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


                cartViewModel.setOrder(total,cartProductList);
                Navigation.findNavController(view)
                        .navigate(R.id.action_cartFragment_to_shippingDetailsFragment);

                break;
            case R.id.save_later:
                progressBar.setVisibility(View.VISIBLE);

                cartViewModel.insertCart(cartProductList).observe(this, new Observer<Result<String>>() {
                    @Override
                    public void onChanged(Result<String> cart) {
                        if (cart!=null){
                            if (cart.getStatus()) {
                                String s = cart.getMessage();
                                progressBar.setVisibility(View.INVISIBLE);
                                ((MainActivity)requireActivity()).ShowSnackBarMsg(s, android.R.color.holo_green_dark);

                            }else{
                                ((MainActivity)requireActivity()).ShowSnackBarMsg(cart.getMessage(), android.R.color.holo_red_light);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });

                break;
        }
    }
    private void enableSwipeToDeleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
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
    public void onDestroyView() {
        cartViewModel.deleteOfflineCart();

        super.onDestroyView();
    }


}
