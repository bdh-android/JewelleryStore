package com.example.e_commerce_store.products.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_store.MainActivity;
import com.example.e_commerce_store.R;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.products.data.models.Category;
import com.example.e_commerce_store.products.ui.adapters.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoryFragment extends Fragment {


    CategoryViewModel categoryViewModel;
    CategoryAdapter   parentItemAdapter;
    List<Category>    arrayList;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitle("Jewellery Store");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id =item.getItemId();
                if (id==R.id.profile){
                    Navigation.findNavController(view)
                            .navigate(R.id.action_categoryFragment_to_profileFragment);
                    return true;
                }else if (id==R.id.cart){
                    Navigation.findNavController(view)
                            .navigate(R.id.action_categoryFragment_to_cartFragment);
                    return true;
                }
                return false;
            }
        });
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        arrayList = new ArrayList<>();

        progressBar = view.findViewById(R.id.progressbar);
        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.parent_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        parentItemAdapter = new CategoryAdapter(arrayList, getActivity());


        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);
        categoriesList();

    }
    private void categoriesList() {
        categoryViewModel.categories.observe(getViewLifecycleOwner(), new Observer<Result<List<Category>>>() {
            @Override
            public void onChanged(Result<List<Category>> categoryResponse) {

                if (categoryResponse != null) {
                    if (categoryResponse.getStatus()) {

                        parentItemAdapter.submitCategoryList(categoryResponse.getData());
                        parentItemAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {

                        ((MainActivity)getActivity()).ShowSnackBarMsg(categoryResponse.getMessage(), android.R.color.holo_red_light);
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                }
            }
        });

    }
}
