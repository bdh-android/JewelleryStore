package com.example.e_commerce_store.profile.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_store.MainActivity;
import com.example.e_commerce_store.R;
import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.profile.ui.adapters.OrdersAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrdersFragment extends Fragment {
    private OrderViewModel orderViewModel;
    private List<Order> orderList;
    private RecyclerView recyclerView;
    private OrdersAdapter ordersAdapter;
    private TextView noOrders;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        orderList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview);
        noOrders = view.findViewById(R.id.no_orders);
        progressBar = view.findViewById(R.id.progressbar);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ordersAdapter = new OrdersAdapter(orderList, this.getActivity());
        recyclerView.setAdapter(ordersAdapter);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        progressBar.setVisibility(View.VISIBLE);
        orderViewModel.getUserOrders()
                .observe(getViewLifecycleOwner(), new Observer<Result<List<Order>>>() {
                    @Override
                    public void onChanged(Result<List<Order>> orders) {
                        if (orders.getStatus()) {
                            orderList = orders.getData();
                            if (orderList.isEmpty())
                                noOrders.setVisibility(View.VISIBLE);
                            ordersAdapter.submitOrdersList(orderList);

                        } else {
                            ((MainActivity) getActivity()).ShowSnackBarMsg(orders.getMessage(), android.R.color.holo_red_light);

                        }
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                });

    }


}
