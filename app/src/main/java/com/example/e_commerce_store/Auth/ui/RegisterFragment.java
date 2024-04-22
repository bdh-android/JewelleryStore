package com.example.e_commerce_store.Auth.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.e_commerce_store.R;
import com.example.e_commerce_store.core.utils.Constants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {
    TextInputLayout nl,el,pl;
    TextInputEditText name,email,password;
    MaterialButton register_btn;
    TextView      log_in;

    AuthViewModel authViewModel;
    ProgressBar  progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nl=view.findViewById(R.id.layout_name);
        el=view.findViewById(R.id.layout);
        pl=view.findViewById(R.id.layout_password);
        name=view.findViewById(R.id.register_name);
        email=view.findViewById(R.id.register_email);
        password=view.findViewById(R.id.register_password);
        log_in=view.findViewById(R.id.log_in);
        register_btn=view.findViewById(R.id.register);
        progressBar=view.findViewById(R.id.progressbar);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        register_btn.setOnClickListener(view1 -> {

            progressBar.setVisibility(View.VISIBLE);
            register_btn.setEnabled(false);
            String n=name.getText().toString();
            String e=email.getText().toString();
            String p=password.getText().toString();
            //String key= Constants.API_KEY;

            authViewModel.registerUser(n,e,p).observe(getViewLifecycleOwner(), registerResponse -> {

                if (registerResponse!=null){
                    if (registerResponse.getStatus()) {
                        if (registerResponse.getErrorNumber().equals(Constants.SUCCESS_CODE_API)) {
                            String message = registerResponse.getMessage();
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            register_btn.setEnabled(true);
                        } else {
                            Validate(registerResponse.getErrorNumber(), registerResponse.getMessage());
                            progressBar.setVisibility(View.INVISIBLE);
                            register_btn.setEnabled(true);
                        }
                    }else{
                        Toast.makeText(getActivity(), registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        register_btn.setEnabled(true);
                    }
                }
            });

        });


        log_in.setOnClickListener(v-> {
            Navigation.findNavController(v).navigateUp();
        });
    }




    private void Validate(String errNum,String msg){
        el.setError(null);
        pl.setError(null);
        nl.setError(null);

        switch (errNum){
            case Constants.ERROR_CODE_VALIDATION_AUTH_NAME:
                nl.setError(msg);
                name.requestFocus();
                break;
            case Constants.ERROR_CODE_VALIDATION_AUTH_EMAIL:
                el.setError(msg);
                email.requestFocus();
                break;
            case Constants.ERROR_CODE_VALIDATION_AUTH_PASSWORD:
                pl.setError(msg);
                password.requestFocus();
                break;

        }
    }
}
