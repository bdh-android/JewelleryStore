package com.example.e_commerce_store.profile.ui.profile;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.e_commerce_store.R;
import com.example.e_commerce_store.core.models.Result;
import com.google.android.material.button.MaterialButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    ProfileViewModel  profileViewModel;
    private ProgressBar progressBar;
    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel =new ViewModelProvider(this).get(ProfileViewModel.class);
        TextView userName = view.findViewById(R.id.user_name_field);
        TextView userEmail = view.findViewById(R.id.user_email_field);
        TextView userAddress1 = view.findViewById(R.id.user_adress_field1);
        TextView userAddress2 = view.findViewById(R.id.user_adress_field2);
        TextView userPhone = view.findViewById(R.id.user_phone_field);
        TextView userZip = view.findViewById(R.id.user_zip_field);
        TextView userCountry = view.findViewById(R.id.user_country_field);
        TextView userState = view.findViewById(R.id.user_state_field);
        TextView userCity = view.findViewById(R.id.user_city_field);
        MaterialButton goToOrederScreen = view.findViewById(R.id.open_orders_screen);
        MaterialButton goToPasswordResetScreen = view.findViewById(R.id.open_reset_password_screen);
        MaterialButton logout = view.findViewById(R.id.logout);
        progressBar = view.findViewById(R.id.progressBar3);

        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), profile -> {
            userName.setText(profile.name != null ?profile.name :"__");
            userEmail.setText(profile.email != null ?profile.email :"__");
            userAddress1.setText(profile.address1 != null ?profile.address1 :"__");
            userAddress2.setText(profile.address2 != null ?profile.address2 :"__");
            userPhone.setText(profile.phone != null ?profile.phone :"__");
            userZip.setText(profile.zip != null ?profile.zip :"__");
            userCountry.setText(profile.country != null ?profile.country :"__");
            userState.setText(profile.state != null ?profile.state :"__");
            userCity.setText(profile.city != null ?profile.city :"__");
        });
        goToOrederScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_ordersFragment);
            }
        });
        goToPasswordResetScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_passwordFragment);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToOrederScreen.setEnabled(false);
                goToPasswordResetScreen.setEnabled(false);
                logout.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                profileViewModel.Logout().observe(getViewLifecycleOwner(), new Observer<Result<String>>() {
                    @Override
                    public void onChanged(Result<String> logoutResponse) {
                        if (logoutResponse != null) {
                            if (logoutResponse.getStatus()) {
                                String msg = logoutResponse.getMessage();
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_signInFragment2);
                            } else {
                                goToOrederScreen.setEnabled(true);
                                goToPasswordResetScreen.setEnabled(true);
                                logout.setEnabled(true);
                                Toast.makeText(getActivity(), logoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });
    }
}