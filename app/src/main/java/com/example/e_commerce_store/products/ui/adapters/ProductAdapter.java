package com.example.e_commerce_store.products.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce_store.R;
import com.example.e_commerce_store.products.data.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    Context context;
    private final List<Product> ChildItemList;


    ProductAdapter(List<Product> childItemList, Context context )
    {
        this.ChildItemList = childItemList;
        this.context=context;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row_cat,parent,false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product childItem = ChildItemList.get(position);

        holder.bindData(childItem);
        holder.ChildItemTitle.setText(childItem.getName());
        holder.ChildItemTitle.setSelected(true);
        holder.price.setText("$."+childItem.getPrice());

        Glide.with(context)
                .load(childItem.getImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.product_image);

    }

    @Override
    public int getItemCount() {
        return ChildItemList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ChildItemTitle,price;

        int product_id;
        ImageView product_image;
        ProductViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ChildItemTitle = itemView.findViewById(R.id.child_item_title);
            price=itemView.findViewById(R.id.product_price);

            product_image=itemView.findViewById(R.id.product_image);


        }

        void bindData( Product childItem){
            this.product_id=childItem.getId();
        }

        @Override
        public void onClick(View view) {
            Bundle bundle =new Bundle();
            bundle.putInt("pass_product_id",product_id);
            Navigation.findNavController(view)
                    .navigate(R.id.action_categoryFragment_to_productDetialsFragment,bundle);



        }
    }

}
