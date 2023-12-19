package com.example.e_commerce_store;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.e_commerce_store.adapters.OrdersAdapter;
import com.example.e_commerce_store.models.Order;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.viewmodel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseActivity {


    OrderViewModel orderViewModel;
    List<Order> orderList;
    RecyclerView recyclerView;
    OrdersAdapter ordersAdapter;
    TextView noOrders;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        orderList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        noOrders=findViewById(R.id.no_orders);
        progressBar=findViewById(R.id.progressbar);


        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ordersAdapter=new OrdersAdapter(orderList,this);
        recyclerView.setAdapter(ordersAdapter);

        orderViewModel=new ViewModelProvider(this).get(OrderViewModel.class);
        int userId= SaveToSharedPreferance.getUserId(this);
        String token=SaveToSharedPreferance.getToken(this);

        progressBar.setVisibility(View.VISIBLE);
        orderViewModel.getUserOrders(Constants.API_KEY,userId,"Bearer "+token).observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                if (orders!=null){

                    orderList=orders;
                    if (orderList.isEmpty())
                        noOrders.setVisibility(View.VISIBLE);
                    ordersAdapter.submitOrdersList(orderList);

                }
                progressBar.setVisibility(View.INVISIBLE);
            }

        });
    }
}
