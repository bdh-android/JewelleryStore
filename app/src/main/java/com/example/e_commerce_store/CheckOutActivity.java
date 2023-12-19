package com.example.e_commerce_store;

import android.os.Bundle;

import com.example.e_commerce_store.ui.shippingdetails.ShippingDetailsFragment;

public class CheckOutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ShippingDetailsFragment.newInstance())
                    .commitNow();
        }
    }
}
