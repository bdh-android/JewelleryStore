package com.example.e_commerce_store;

import android.content.Intent;
import android.os.Bundle;

import com.example.e_commerce_store.models.RegisterResponse;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.viewmodel.UserViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Toast;

public class SplashActivity extends BaseActivity{

    UserViewModel userViewModel;
    String token;
    boolean isConnected=false;
    boolean lock=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        SaveToSharedPreferance.deleteCart(this);
        userViewModel= new ViewModelProvider(this).get(UserViewModel.class);
        token= SaveToSharedPreferance.getToken(this);



    }

    private void getUser(String apiKey,String Bearertoken){
        userViewModel.getMe(apiKey, Bearertoken).observe(this, new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse registerResponse) {
                if (registerResponse!=null){
                    if (registerResponse.isStatus()){

                        SaveToSharedPreferance.setUserId(SplashActivity.this,registerResponse.getUser().getId());
                        Toast.makeText(SplashActivity.this, "welcome "+registerResponse.getUser().getName(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SplashActivity.this,CategoryActivity.class);
                        intent.putExtra("token",token);
                        startActivity(intent);
                        finish();

                    } else{
                        //open login screen and display error in case token issues
                        Toast.makeText(SplashActivity.this, registerResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashActivity.this,SignInActivity.class));
                        finish();

                    }
                }
            }
        });
    }

    //network state changes
    @Override
    public void onChanged(boolean isConn) {
        super.onChanged(isConn);
        isConnected=isConn;
        if (isConnected&&lock){
            getUser(Constants.API_KEY,"Bearer "+token);
            lock=false;
        }

    }
}
