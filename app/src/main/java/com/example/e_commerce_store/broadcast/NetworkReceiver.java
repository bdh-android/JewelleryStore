package com.example.e_commerce_store.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.e_commerce_store.utils.NetworkUtil;
import com.example.e_commerce_store.utils.OnNetworkChanged;

public class NetworkReceiver extends BroadcastReceiver {

    OnNetworkChanged onNetworkChanged;
    @Override
    public void onReceive(Context context, Intent intent) {


        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            boolean s =NetworkUtil.isNetworkConnected(context.getApplicationContext());
            onNetworkChanged.onChanged(s);

        }

    }

    public void setOnNetworkChanged(OnNetworkChanged onNetworkChanged) {
        this.onNetworkChanged = onNetworkChanged;
    }
}
