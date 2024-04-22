package com.example.e_commerce_store.products.ui;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.products.data.ProductsReposetory;
import com.example.e_commerce_store.products.data.models.Category;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CategoryViewModel extends ViewModel {
    //CategoryRepository categoryRepository;
    ProductsReposetory productsReposetory;
    LiveData<Result<List<Category>>> categories ;
    @Inject
    public CategoryViewModel(ProductsReposetory productsReposetory) {
        this.productsReposetory = productsReposetory;
        getCategory();
    }

    public void getCategory(){
        categories =  productsReposetory.getAllCategories();
        //return categories;
    }
}
