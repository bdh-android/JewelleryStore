package com.example.e_commerce_store.profile.ui.resetpassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_commerce_store.R;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.Constants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PasswordFragment extends Fragment implements View.OnClickListener {

    private TextInputLayout l_old_pass,l_new_pass,l_confirm_pass;
    private TextInputEditText old_pass,new_pass,confirm_pass;
    private MaterialButton updatePass;
    private ProgressBar progressBar;
    private PasswordViewModel passwordViewModel;
    private String oldPassword,newPassword,confirmPassword;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        passwordViewModel=new ViewModelProvider(this).get(PasswordViewModel.class);

    }


    void init(View view){
        l_old_pass=view.findViewById(R.id.layout_old_pass);
        l_new_pass=view.findViewById(R.id.layout_new_pass);
        l_confirm_pass=view.findViewById(R.id.layout_confirm_pass);
        old_pass=view.findViewById(R.id.old_pass);
        new_pass=view.findViewById(R.id.new_pass);
        confirm_pass=view.findViewById(R.id.confirm_pass);
        updatePass=view.findViewById(R.id.update);
        progressBar=view.findViewById(R.id.progressbar);
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
        passwordViewModel.changePassword(oldPassword,newPassword,confirmPassword)
                .observe(getViewLifecycleOwner(), new Observer<Result<String>>() {
                    @Override
                    public void onChanged(Result<String> passwordResponse) {
                        if (passwordResponse!=null){
                            if (passwordResponse.getStatus()) {
                                if (Constants.SUCCESS_CODE_API.equals(passwordResponse.getErrorNumber())) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    ShowMessage(passwordResponse.getMessage());
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Validate(passwordResponse.getErrorNumber(), passwordResponse.getMessage());
                                }
                            }else{
                                progressBar.setVisibility(View.INVISIBLE);

                                ShowMessage(passwordResponse.getMessage());
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
            case Constants.ERROR_CODE_VALIDATION_NEW_PASSWORD:
                l_new_pass.setError(msg);
                break;
            case Constants.ERROR_CODE_VALIDATION_CONFIRM_PASSWORD:
                l_confirm_pass.setError(msg);
                break;
            case Constants.ERROR_CODE_VALIDATION_OLD_PASSWORD:
                l_old_pass.setError(msg);
                break;
            case Constants.ERROR_CODE_VALIDATION_WRONG_OLD_PASSWORD:
                ShowMessage(msg);
                break;
            case Constants.ERROR_CODE_VALIDATION_OLD_NEW_PASSWORD_MATCHES:
                ShowMessage(msg);
                break;
            case Constants.SERVER_ERROR:
                ShowMessage(msg);
        }
    }
    void ShowMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

}
