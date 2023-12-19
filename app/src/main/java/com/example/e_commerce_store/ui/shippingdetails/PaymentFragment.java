package com.example.e_commerce_store.ui.shippingdetails;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.example.e_commerce_store.R;
import com.example.e_commerce_store.models.Order;
import com.example.e_commerce_store.models.OrderResponse;
import com.example.e_commerce_store.models.PaymentRespone;
import com.example.e_commerce_store.models.PaymentResult;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.utils.Util;
import com.example.e_commerce_store.viewmodel.OrderViewModel;
import com.google.gson.JsonObject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class PaymentFragment extends Fragment {


    private static final int REQUEST_CODE=1203;
    private String token="";
    private String client_token="";
    private String value;
    private PaymentViewModel mViewModel;
    private OrderViewModel orderViewModel;
    private int userId;
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        progressBar=view.findViewById(R.id.progressbar);
        pay_state_img=view.findViewById(R.id.pay_state_img);
        pay_state=view.findViewById(R.id.pay_state);

        Button pay=view.findViewById(R.id.pay);
         pay.setEnabled(false);
         token= SaveToSharedPreferance.getToken(getActivity());
         order=getOrder();
         value=String.valueOf(order.getTotal_price());
        userId=SaveToSharedPreferance.getUserId(getActivity());

        mViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        orderViewModel=new ViewModelProvider(this).get(OrderViewModel.class);
       mViewModel.getToken(Constants.API_KEY,"Bearer "+token).observe(getActivity(), new Observer<PaymentRespone>() {
           @Override
           public void onChanged(PaymentRespone paymentRespone) {
               if (paymentRespone!=null){
                   if (paymentRespone.isStatus()){

                       client_token=paymentRespone.getToken();
                       pay.setEnabled(true);
                       progressBar.setVisibility(View.INVISIBLE);
                   }else{
                       Toast.makeText(getActivity(), paymentRespone.getMsg(), Toast.LENGTH_SHORT).show();
                       pay.setEnabled(true);
                       progressBar.setVisibility(View.INVISIBLE);
                   }
               }

           }
       });


       pay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DropInRequest dropInRequest = new DropInRequest()
                       .clientToken(client_token);
               startActivityForResult(dropInRequest.getIntent(getActivity()), REQUEST_CODE);
           }
       });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                 nonce=result.getPaymentMethodNonce().getNonce();


                JsonObject params= Util.buildOrderRequest(order.getUser_id(),order.getTotal_price(),order.getProducts());
                makeOrder(Constants.API_KEY,params,token);

            } else if (resultCode == RESULT_CANCELED) {
                // the user canceled

            } else {

                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void makeOrder(String apiKey, JsonObject data, String token){
        progressBar.setVisibility(View.VISIBLE);
        orderViewModel.makeOrder(apiKey, data, "Bearer "+token).observe(this, new Observer<OrderResponse>() {
            @Override
            public void onChanged(OrderResponse orderResponse) {
                if (orderResponse!=null){
                    if (orderResponse.isStatus()){
                        int orderId=orderResponse.getOrder_id();

                        if (orderId!=0)
                            proceedPayment(Constants.API_KEY,orderId,userId,value,nonce,token);
                        progressBar.setVisibility(View.INVISIBLE);
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Some thing went wrong try agian", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void proceedPayment(String apiKey,int orderId,int userId,String amount,String nonce,String token){

        mViewModel.getPaymentState(apiKey, orderId,userId, amount, nonce, "Bearer "+token).observe(this, new Observer<PaymentResult>() {
            @Override
            public void onChanged(PaymentResult paymentResult) {
                if (paymentResult!=null){
                    boolean success=paymentResult.isSuccess();
                    if (success) {
                        pay_state_img.setBackgroundResource(R.drawable.success);
                        pay_state.setText(R.string.e_success);
                        pay_state.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                        clearOrderAndCart();
                    }else {
                        pay_state_img.setBackgroundResource(R.drawable.failed);
                        pay_state.setText(R.string.e_failed);
                        pay_state.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }





   private Order getOrder(){
       return SaveToSharedPreferance.getOrder(getActivity());
   }

   private void clearOrderAndCart(){
       Thread thread=new Thread(){
           @Override
           public void run() {
               SaveToSharedPreferance.deleteCart(getActivity());
               SaveToSharedPreferance.deleteOrder(getActivity());
           }
       };
       thread.start();

   }
}
