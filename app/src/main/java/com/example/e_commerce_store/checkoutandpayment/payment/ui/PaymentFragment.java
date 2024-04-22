package com.example.e_commerce_store.checkoutandpayment.payment.ui;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.example.e_commerce_store.R;

import com.example.e_commerce_store.checkoutandpayment.payment.data.model.PaymentResult;
import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.Util;
import com.google.gson.JsonObject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PaymentFragment extends Fragment {
    private static final int REQUEST_CODE=1203;
    private String client_token="";
    private String value;
    private PaymentViewModel mViewModel;
    private String nonce;
    private Order order;
    ImageView pay_state_img;
    TextView pay_state;
    private ProgressBar progressBar;

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.payment_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        progressBar=view.findViewById(R.id.progressbar);
        pay_state_img=view.findViewById(R.id.pay_state_img);
        pay_state=view.findViewById(R.id.pay_state);

        Button pay=view.findViewById(R.id.pay);
        pay.setEnabled(false);


        mViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        order=mViewModel.getOrder();
        value=String.valueOf(order.getTotal_price());

        mViewModel.getToken()
                .observe(getViewLifecycleOwner(), paymentRespone -> {
                    if (paymentRespone!=null){
                        if (paymentRespone.getStatus()){
                            client_token=paymentRespone.getData();
                            pay.setEnabled(true);
                            progressBar.setVisibility(View.INVISIBLE);
                        }else{
                            Toast.makeText(getActivity(), paymentRespone.getMessage(), Toast.LENGTH_SHORT).show();
                            pay.setEnabled(true);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                });


        pay.setOnClickListener(view1 -> {
            DropInRequest dropInRequest = new DropInRequest()
                    .clientToken(client_token);
            startActivityForResult(dropInRequest.getIntent(getActivity()), REQUEST_CODE);
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && data!=null) {
            if (resultCode == RESULT_OK) {

                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                nonce=result.getPaymentMethodNonce().getNonce();
                JsonObject params= Util.buildOrderRequest(order.getUser_id(),order.getTotal_price(),order.getProducts());
                makeOrder(params);

            } else if (resultCode == RESULT_CANCELED) {
                // the user canceled

            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makeOrder(JsonObject data){
        progressBar.setVisibility(View.VISIBLE);
        mViewModel.makeOrder(data).observe(getViewLifecycleOwner(), orderResponse -> {
            if (orderResponse!=null){
                if (orderResponse.getStatus()){
                    int orderId=orderResponse.getData();

                    if (orderId!=0)
                        proceedPayment(orderId,value,nonce);
                    else progressBar.setVisibility(View.INVISIBLE);
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Some thing went wrong try agian", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void proceedPayment(int orderId,String amount,String nonce){

        mViewModel.getPaymentState(orderId,amount, nonce).observe(getViewLifecycleOwner(), paymentResult -> {
            if (paymentResult!=null){
                boolean success=paymentResult.getStatus();
                if (success) {
                    pay_state_img.setBackgroundResource(R.drawable.success);
                    pay_state.setText(R.string.e_success);
                    pay_state.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                    mViewModel.clearOrderAndCart();
                }else {
                    pay_state_img.setBackgroundResource(R.drawable.failed);
                    pay_state.setText(R.string.e_failed);
                    pay_state.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }






}
