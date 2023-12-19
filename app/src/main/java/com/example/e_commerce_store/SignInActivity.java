package com.example.e_commerce_store;


import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.e_commerce_store.net.APIClient;
import com.example.e_commerce_store.net.APIInterface;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.viewmodel.LoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends BaseActivity {


    APIInterface apiInterface;
    TextInputEditText email,password;
    MaterialButton login_btn;
    LoginViewModel loginViewModel;
    ProgressBar progressBar;
    TextInputLayout el,pl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        el=findViewById(R.id.layout);
        pl=findViewById(R.id.layout_password);
        TextView nocount=findViewById(R.id.no_account);
         email=findViewById(R.id.login_email);
         password=findViewById(R.id.login_password);
         login_btn=findViewById(R.id.login);
         progressBar=findViewById(R.id.progress);
        apiInterface= APIClient.getInstance().getApi();

       loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);




        login_btn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);

            login_btn.setEnabled(false);
            String e=email.getText().toString();
            String p=password.getText().toString();
            String apiKey= Constants.API_KEY;
            loginViewModel.getLoginResponse(e,p,apiKey).observe(SignInActivity.this, loginResponse -> {


            if (loginResponse!=null) {
                if (loginResponse.isStatus()) {
                    if (loginResponse.getErrorNumber().equals("S000")) {
                        String t = loginResponse.getToken().getToken();

                        SaveToSharedPreferance.setToken(SignInActivity.this, t);
                        SaveToSharedPreferance.setUserId(SignInActivity.this, loginResponse.getToken().getUser_id());

                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(SignInActivity.this, CategoryActivity.class);
                        intent.putExtra("token", t);

                        startActivity(intent);
                        finish();
                    } else {

                        Validate(loginResponse.getErrorNumber(), loginResponse.getMsg());
                        progressBar.setVisibility(View.INVISIBLE);
                        login_btn.setEnabled(true);
                    }
                }else{
                    //if there an error in token or server error 500 or onfailure retrofit e.g no internet connecton
                    progressBar.setVisibility(View.INVISIBLE);
                    login_btn.setEnabled(true);
                    Toast.makeText(SignInActivity.this, loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            });
        });


        nocount.setOnClickListener(view -> startActivity(new Intent(SignInActivity.this,RegisterActivity.class)));


    }

    private void Validate(String errNum,String msg){
        el.setErrorEnabled(false);
        pl.setErrorEnabled(false);
        el.setError(null);
        pl.setError(null);
       switch (errNum){
           case "E002":
               el.setErrorEnabled(true);
               el.setError(msg);

               break;
           case "E003":
               pl.setErrorEnabled(true);
               pl.setError(msg);

               break;
           case "U000":
               Toast.makeText(SignInActivity.this,msg,Toast.LENGTH_LONG).show();
               break;
       }
    }

}
