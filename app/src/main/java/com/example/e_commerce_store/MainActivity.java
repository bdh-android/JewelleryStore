package com.example.e_commerce_store;

import android.os.Bundle;
import android.widget.Toast;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.e_commerce_store.Auth.data.models.User;
import com.example.e_commerce_store.Auth.ui.AuthViewModel;
import com.example.e_commerce_store.core.models.Result;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {
    private AuthViewModel authViewModel;
    boolean isConnected=false;
    public boolean shouldDisplaySplachScreen = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen= SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(()-> {
            return shouldDisplaySplachScreen;
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.getUser().observe(this, new Observer<Result<User>>() {
            @Override
            public void onChanged(Result<User> userResult) {
                if (userResult!=null){
                    if (userResult.getStatus()){
                         shouldDisplaySplachScreen = false;
                        Toast.makeText(MainActivity.this, "welcome "+userResult.getData().getName(), Toast.LENGTH_SHORT).show();
                    } else{
                        NavController navController= Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment_auth);
                        navController.navigate(R.id.action_categoryFragment_to_signInFragment2);
                        shouldDisplaySplachScreen = false;
                        //Toast.makeText(MainActivity.this, userResult.getMessage(), Toast.LENGTH_SHORT).show();
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

    }
}
