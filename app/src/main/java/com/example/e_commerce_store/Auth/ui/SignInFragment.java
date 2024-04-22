package com.example.e_commerce_store.Auth.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
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
public class SignInFragment extends Fragment {


    private static final String TAG = "SignInFragment";

    TextInputEditText email, password;
    MaterialButton login_btn;
    AuthViewModel authViewModel;
    ProgressBar progressBar;
    TextInputLayout el, pl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        el = v.findViewById(R.id.layout);
        pl = v.findViewById(R.id.layout_password);
        TextView nocount = v.findViewById(R.id.no_account);
        email = v.findViewById(R.id.login_email);
        password = v.findViewById(R.id.login_password);
        login_btn = v.findViewById(R.id.login);
        progressBar = v.findViewById(R.id.progress);


        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);


        login_btn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);

            login_btn.setEnabled(false);
            String e = email.getText().toString();
            String p = password.getText().toString();

            authViewModel.getLoginResponse(e, p).observe(getViewLifecycleOwner(), loginResponse -> {


                if (loginResponse != null) {
                    if (loginResponse.getStatus()) {
                        if (loginResponse.getErrorNumber().equals(Constants.SUCCESS_CODE_API)) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Navigation.findNavController(view).navigate(R.id.action_signInFragment2_to_categoryFragment);

                        } else {
                            Validate(loginResponse.getErrorNumber(), loginResponse.getMessage());
                            progressBar.setVisibility(View.INVISIBLE);
                            login_btn.setEnabled(true);
                        }
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        login_btn.setEnabled(true);
                        Log.d(TAG, loginResponse.getMessage());
                        Toast.makeText(getActivity(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


        nocount.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_signInFragment2_to_registerFragment2));

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        });
    }


    private void Validate(String errNum, String msg) {
        el.setErrorEnabled(false);
        pl.setErrorEnabled(false);
        el.setError(null);
        pl.setError(null);
        switch (errNum) {
            case Constants.ERROR_CODE_VALIDATION_AUTH_PASSWORD:
                el.setErrorEnabled(true);
                el.setError(msg);

                break;
            case Constants.ERROR_CODE_VALIDATION_AUTH_EMAIL:
                pl.setErrorEnabled(true);
                pl.setError(msg);

                break;
            case "U000":
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                break;
        }
    }

}
