package com.example.e_commerce_store;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce_store.models.RegisterResponse;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.viewmodel.RegisterViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends BaseActivity {



    TextInputLayout nl,el,pl;
    TextInputEditText name,email,password;
    MaterialButton register_btn;
    TextView log_in;
    RegisterViewModel registerViewModel;
     ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        nl=findViewById(R.id.layout_name);
        el=findViewById(R.id.layout);
        pl=findViewById(R.id.layout_password);
        name=findViewById(R.id.register_name);
        email=findViewById(R.id.register_email);
        password=findViewById(R.id.register_password);
        log_in=findViewById(R.id.log_in);
        register_btn=findViewById(R.id.register);
        progressBar=findViewById(R.id.progressbar);

        registerViewModel= new ViewModelProvider(this).get(RegisterViewModel.class);

        register_btn.setOnClickListener(view -> {

             progressBar.setVisibility(View.VISIBLE);
            register_btn.setEnabled(false);
            String n=name.getText().toString();
            String e=email.getText().toString();
            String p=password.getText().toString();
            String key= Constants.API_KEY;

            registerViewModel.registerUser(n,e,p,key).observe(RegisterActivity.this, registerResponse -> {

                if (registerResponse!=null){
                    if (registerResponse.isStatus()) {
                        if (registerResponse.getErrorNumber().equals("S000")) {

                            String userEmail = registerResponse.getUser().getEmail();
                            String userName = registerResponse.getUser().getName();
                            int userId = registerResponse.getUser().getId();
                            String message = registerResponse.getMsg();


                            SaveToSharedPreferance.setUserEmail(RegisterActivity.this, userEmail);
                            SaveToSharedPreferance.setUserName(RegisterActivity.this, userName);
                            SaveToSharedPreferance.setUserId(RegisterActivity.this, userId);

                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            register_btn.setEnabled(true);
                        } else {
                            Validate(registerResponse.getErrorNumber(), registerResponse.getMsg());
                            progressBar.setVisibility(View.INVISIBLE);
                            register_btn.setEnabled(true);
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, registerResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        register_btn.setEnabled(true);
                    }
                }
            });

        });


        log_in.setOnClickListener(view -> {
            onBackPressed();

            finish();
        });
    }



    private void Validate(String errNum,String msg){
        el.setError(null);
        pl.setError(null);
        nl.setError(null);

        switch (errNum){
            case "E001":
                nl.setError(msg);
                name.requestFocus();
                break;
            case "E002":
                el.setError(msg);
                email.requestFocus();
                break;
            case "E003":
                pl.setError(msg);
                password.requestFocus();
                break;

        }
    }
}
