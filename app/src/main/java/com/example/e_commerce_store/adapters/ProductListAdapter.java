package com.example.e_commerce_store.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce_store.ProductDetialsActivity;
import com.example.e_commerce_store.R;

import com.example.e_commerce_store.models.Product;

public class ProductListAdapter extends PagedListAdapter<Product, RecyclerView.ViewHolder> {




    private Context context;

    public ProductListAdapter(Context context) {
        super(Product.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            View viewItem=layoutInflater.inflate(R.layout.product_row,parent,false);

            ProductItemViewHolder viewHolder = new ProductItemViewHolder(viewItem);
            return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            ((ProductItemViewHolder)holder).bindTo(getItem(position));

    }


    public class ProductItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


       private View viewItem;

       TextView productPrice,productName;

       ImageView productImage;
       int product_id;
       public ProductItemViewHolder(View viewItem) {
           super(viewItem);
           viewItem.setOnClickListener(this);

           this.viewItem=viewItem;
           productImage=viewItem.findViewById(R.id.product_image);
           productPrice=viewItem.findViewById(R.id.product_price);
           productName=viewItem.findViewById(R.id.child_item_title);


       }
        public void bindTo(Product product) {
            this.product_id=product.getId();
            productName.setText(product.getName());
            productName.setSelected(true);
            productPrice.setText("$."+product.getPrice());
            Glide.with(context)
                    .load(product.getImage())
                    .placeholder(R.drawable.placeholder)
                    .fitCenter()
                    .into(productImage);

        }

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context, ProductDetialsActivity.class);
            intent.putExtra("pass_product_id",product_id);
            view.getContext().startActivity(intent);
        }


    }

}
