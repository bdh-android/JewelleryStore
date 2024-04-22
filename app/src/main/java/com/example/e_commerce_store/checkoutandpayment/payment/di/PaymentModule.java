package com.example.e_commerce_store.checkoutandpayment.payment.di;


import com.example.e_commerce_store.checkoutandpayment.payment.data.PaymentRepository;
import com.example.e_commerce_store.checkoutandpayment.payment.data.PaymentRepositoryImpl;
import com.example.e_commerce_store.checkoutandpayment.payment.data.remote.PaymentApiInterface;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(ViewModelComponent.class)
public abstract class PaymentModule {
    @Provides
    static PaymentApiInterface provideShippingApiInterface(Retrofit retrofit){
        return retrofit.create(PaymentApiInterface.class);
    }
    @Binds
    abstract PaymentRepository bindPaymentRepository(PaymentRepositoryImpl paymentRepository);
}
