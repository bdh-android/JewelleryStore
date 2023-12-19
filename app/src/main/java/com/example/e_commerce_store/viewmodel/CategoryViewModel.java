package com.example.e_commerce_store.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce_store.models.CategoryResponse;
import com.example.e_commerce_store.repository.CategoryRepository;

public class CategoryViewModel extends AndroidViewModel {
    CategoryRepository categoryRepository;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository=new CategoryRepository(application);
    }

    public LiveData<CategoryResponse> getCategory(String apiKey, String bearerToken){
        return categoryRepository.getAllCategories(apiKey,bearerToken);
    }
}
