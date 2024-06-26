package com.example.e_commerce_store.products.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_store.R;
import com.example.e_commerce_store.products.data.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {


    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Category> itemList;
    Context context;
    private boolean isConnected=true;
    ProductAdapter childItemAdapter;
    public CategoryAdapter(List<Category> itemList,Context context)
    {
        this.itemList = itemList;
        this.context=context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category parentItem = itemList.get(position);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.productsRecyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);


        layoutManager.setInitialPrefetchItemCount(parentItem
                .getProductList()
                .size());


        childItemAdapter = new ProductAdapter(parentItem.getProductList(),context );

        holder.productsRecyclerView.setLayoutManager(layoutManager);
        holder.productsRecyclerView.setAdapter(childItemAdapter);
        holder.productsRecyclerView.setRecycledViewPool(viewPool);

        SpannableString spannableString = new SpannableString(parentItem.getName());
        spannableString.setSpan(new UnderlineSpan(),0,spannableString.length(),0);

        holder.categoryTitle.setText(spannableString);

        holder.categoryTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected) {
                    Bundle bundle =new Bundle();
                    bundle.putInt("cat_id",parentItem.getId());
                    Navigation.findNavController(view).navigate(R.id.action_categoryFragment_to_productFragment,
                            bundle);

                }else{
                    Toast.makeText(context, "Please Check your network connection.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView categoryTitle;
        private final RecyclerView productsRecyclerView;

        CategoryViewHolder(final View itemView) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.parent_item_title);
            productsRecyclerView = itemView.findViewById(R.id.child_recyclerview);

        }


    }
    public void isConnectedToNetwork(boolean isConnected){
        this.isConnected=isConnected;



    }
    public void submitCategoryList(List<Category> itemList){
        this.itemList=itemList;
        notifyDataSetChanged();
    }
}
