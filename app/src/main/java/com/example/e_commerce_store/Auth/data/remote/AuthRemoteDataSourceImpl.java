package com.example.e_commerce_store.Auth.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce_store.Auth.data.models.Token;
import com.example.e_commerce_store.Auth.data.models.User;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.SharedPrefs;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRemoteDataSourceImpl implements AuthRemoteDataSource {
    private AuthApiInterface apiInterface;
    private SharedPrefs sharedPrefs;

    @Inject
    public AuthRemoteDataSourceImpl(AuthApiInterface apiInterface,SharedPrefs sharedPrefs) {
        this.apiInterface = apiInterface;
        this.sharedPrefs=sharedPrefs;
    }

    @Override
    public LiveData<Result<User>> createNewUser(String name, String email,String password) {
            MutableLiveData<Result<User>> mutableLiveData = new MutableLiveData<>();
            apiInterface.createNewUser(name,email,password).enqueue(new Callback<Result<User>>() {
                @Override
                public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                    if (response.isSuccessful()) {
                        Result<User> res = response.body();
                        if (res!=null) {
                            User user = res.getData();
                            if (user != null) {
                                sharedPrefs.setUserName(user.getName());
                                sharedPrefs.setUserEmail(user.getEmail());
                                sharedPrefs.setUserId(user.getId());
                            }
                        }
                        mutableLiveData.setValue(res);
                    }else {

                        Result<User> res=new Result<User>(false,String.valueOf(response.code()),response.message());
                        mutableLiveData.setValue(res);
                    }
                }

                @Override
                public void onFailure(Call<Result<User>> call, Throwable t) {

                    Result<User> res=new Result<User>(false,"R000",t.getMessage());
                    mutableLiveData.setValue(res);

                }
            });

            return mutableLiveData;
        }




    @Override
    public LiveData<Result<Token>> loginUser(String email, String password) {
        MutableLiveData<Result<Token>> mutableLiveData = new MutableLiveData<>();
        apiInterface.loginUser(email, password).enqueue(new Callback<Result<Token>>() {

            @Override
            public void onResponse(Call<Result<Token>> call, Response<Result<Token>> response) {

                if (response.isSuccessful() && response != null) {
                    Result<Token> res = response.body();
                    if (res.getData()!=null)
                        sharedPrefs.setToken(res.getData().getToken());
                    mutableLiveData.setValue(res);

                } else {
                    Result<Token> res=new Result<Token>(false,String.valueOf(response.code()),response.message());
                    mutableLiveData.setValue(res);

                }
            }

            @Override
            public void onFailure(Call<Result<Token>> call, Throwable t) {

                Result<Token> res=new Result<Token>(false,"R000",t.getMessage());
                mutableLiveData.setValue(res);
            }
        });

        return mutableLiveData;
    }

    @Override
    public LiveData<Result<User>> getUser() {
        MutableLiveData<Result<User>> mutableLiveData = new MutableLiveData<>();
        apiInterface.getUser().enqueue(new Callback<Result<User>>() {
            @Override
            public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                if (response.isSuccessful() && response != null) {
                    Result<User> res = response.body();
                    if (res!=null) {
                        User user = res.getData();
                        if(user!=null) {
                            sharedPrefs.setUserName(user.getName());
                            sharedPrefs.setUserEmail(user.getEmail());
                            sharedPrefs.setUserId(user.getId());
                        }
                    }
                    mutableLiveData.setValue(res);
                }else {
                    Result<User> res=new Result<User>(false,String.valueOf(response.code())," server error");
                    mutableLiveData.setValue(res);
                }
            }

            @Override
            public void onFailure(Call<Result<User>> call, Throwable t) {
                Result<User> res=new Result<User>(false,"R000",t.getMessage());
                mutableLiveData.setValue(res);
            }
        });

        return mutableLiveData;
    }
}
