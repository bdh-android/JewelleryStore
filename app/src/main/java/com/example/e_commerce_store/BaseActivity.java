package com.example.e_commerce_store;


import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce_store.broadcast.NetworkReceiver;
import com.example.e_commerce_store.utils.OnNetworkChanged;
import com.google.android.material.snackbar.Snackbar;


public class BaseActivity extends AppCompatActivity implements OnNetworkChanged {
    NetworkReceiver networkReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        networkReceiver=new NetworkReceiver();
        networkReceiver.setOnNetworkChanged(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
       registerReceiver(networkReceiver,new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
       }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkReceiver);

    }

    Snackbar snackbar;

    @Override
    public void onChanged(boolean isConn) {


        if (!isConn)
            ShowSnackBarMsg("No Network..Please turn on your wifi! ",android.R.color.holo_red_light);

    }

    void ShowSnackBarMsg(String msg,int color){

            snackbar = Snackbar.make(this.getWindow().getDecorView().findViewById(android.R.id.content),
                    msg, Snackbar.LENGTH_LONG);

            snackbar.setBackgroundTint(getResources().getColor(color));

        snackbar.show();

    }
}
