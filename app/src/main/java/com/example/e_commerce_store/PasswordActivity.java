package com.example.e_commerce_store;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_commerce_store.models.PasswordResponse;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.viewmodel.PasswordViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PasswordActivity extends BaseActivity implements View.OnClickListener {

    TextInputLayout l_old_pass,l_new_pass,l_confirm_pass;
    TextInputEditText old_pass,new_pass,confirm_pass;
    MaterialButton updatePass;
    ProgressBar progressBar;
    PasswordViewModel passwordViewModel;
    String oldPassword,newPassword,confirmPassword;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        init();
        passwordViewModel=new ViewModelProvider(this).get(PasswordViewModel.class);
        token= SaveToSharedPreferance.getToken(this);
    }

    void init(){
        l_old_pass=findViewById(R.id.layout_old_pass);
        l_new_pass=findViewById(R.id.layout_new_pass);
        l_confirm_pass=findViewById(R.id.layout_confirm_pass);
        old_pass=findViewById(R.id.old_pass);
        new_pass=findViewById(R.id.new_pass);
        confirm_pass=findViewById(R.id.confirm_pass);
        updatePass=findViewById(R.id.update);
        progressBar=findViewById(R.id.progressbar);
        updatePass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.update){
            progressBar.setVisibility(View.VISIBLE);
            oldPassword=old_pass.getText().toString();
            newPassword=new_pass.getText().toString();
            confirmPassword=confirm_pass.getText().toString();

            updatePassword(oldPassword,newPassword,confirmPassword);

        }
    }

    void  updatePassword(String oldPassword,String newPassword,String confirmPassword){
        passwordViewModel.changePassword(Constants.API_KEY,oldPassword,newPassword,confirmPassword,"Bearer "+token)
                .observe(this, new Observer<PasswordResponse>() {
                    @Override
                    public void onChanged(PasswordResponse passwordResponse) {
                        if (passwordResponse!=null){
                            if (passwordResponse.isStatus()) {
                                if ("S000".equals(passwordResponse.getErrorNumber())) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    ShowMessage(passwordResponse.getMsg());
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Validate(passwordResponse.getErrorNumber(), passwordResponse.getMsg());
                                }
                            }else{
                                progressBar.setVisibility(View.INVISIBLE);

                                ShowMessage(passwordResponse.getMsg());
                            }
                        }
                    }
                });
    }

    void Validate(String errorCode,String msg){
        l_old_pass.setError(null);
        l_new_pass.setError(null);
        l_confirm_pass.setError(null);

        switch (errorCode){
            case "E0012":
                l_new_pass.setError(msg);
                break;
            case "E0013":
                l_confirm_pass.setError(msg);
                    break;
            case "E0014":
                l_old_pass.setError(msg);
                break;
            case "E0021":
                ShowMessage(msg);
                break;
            case "E0022":
                ShowMessage(msg);
                break;
            case "500":
                ShowMessage(msg);
        }
    }
    void ShowMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
