package com.example.e_commerce_store.checkoutandpayment.shippingdetails.data;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.checkoutandpayment.shippingdetails.data.remote.ShippingApiInterface;
import com.example.e_commerce_store.core.models.Address;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.Constants;
import com.example.e_commerce_store.core.utils.SharedPrefs;

import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingRepositoryImpl implements ShippingRepository{
    ShippingApiInterface shippingApiInterface;
    SharedPrefs sharedPrefs;

    @Inject
    public ShippingRepositoryImpl(ShippingApiInterface shippingApiInterface, SharedPrefs sharedPrefs) {
        this.shippingApiInterface = shippingApiInterface;
        this.sharedPrefs = sharedPrefs;
    }

    public LiveData<Result<Address>> insertShippingAddress(Map<String,String> params){
        int userId = sharedPrefs.getUserId();
        params.put(Constants.USER_ID,String.valueOf(userId));
        saveShippingDetailsToSharedPrefs(params);

        MutableLiveData<Result<Address>> mutableLiveData=new MutableLiveData<>();
        shippingApiInterface.insertShippingDetails(params).enqueue(new Callback<Result<Address>>() {
            @Override
            public void onResponse(Call<Result<Address>> call, Response<Result<Address>> response) {
                if (response.isSuccessful()&&response!=null){
                    Result<Address> shippingDetails=response.body();
                    mutableLiveData.setValue(shippingDetails);
                }else{
                    Result<Address> res=new Result<Address>(false,String.valueOf(response.code()),response.message());
                    mutableLiveData.setValue(res);
                }
            }

            @Override
            public void onFailure(Call<Result<Address>> call, Throwable t) {
                Result<Address> res=new Result<Address>(false,"R000",t.getMessage());
                mutableLiveData.setValue(res);
            }
        });

        return mutableLiveData;
    }



    public LiveData<Address> getShippingDetails(){
        MutableLiveData<Address> mutableLiveData=new MutableLiveData<>();
        Address address = sharedPrefs.getShippingDetails();
        mutableLiveData.setValue(address);

        return mutableLiveData;
    }

    private void saveShippingDetailsToSharedPrefs(Map<String, String> params){
        Address shippingDetails = new Address(
                params.get(Constants.ADDRESS1),
                params.get(Constants.ADDRESS2),
                params.get(Constants.COUNTRY),
                params.get(Constants.STATE),
                params.get(Constants.CITY),
                params.get(Constants.PHONENUMBER),
                params.get(Constants.ZIP)
        );
        sharedPrefs.saveShippingDetails(shippingDetails);
    }
}
