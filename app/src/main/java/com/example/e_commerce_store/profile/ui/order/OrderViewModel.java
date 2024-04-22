package com.example.e_commerce_store.profile.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce_store.core.models.Order;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.profile.data.ProfileRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OrderViewModel extends ViewModel {
    ProfileRepository profileRepository;
    @Inject
    public OrderViewModel(ProfileRepository profileRepository) {

        this.profileRepository = profileRepository;
    }



    public LiveData<Result<List<Order>>> getUserOrders(){

        return  profileRepository.getUserOrders();
    }
}
