package com.example.e_commerce_store.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce_store.ProductDetialsActivity;
import com.example.e_commerce_store.R;
import com.example.e_commerce_store.models.Product;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    private Context context;
    private List<Product>  products;

    public SearchAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_product_row,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
     holder.bindData(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public  void submitList(List<Product> products){
        this.products=products;
        notifyDataSetChanged();
    }



    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

      ImageView image;
      TextView title,price;
      int product_id;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id._image);
            title=itemView.findViewById(R.id._title);
            price=itemView.findViewById(R.id._price);
            itemView.setOnClickListener(this);
        }

        public void bindData(Product product) {
            this.product_id=product.getId();
            Glide.with(context)
                    .load(product.getImage())
                    .placeholder(R.drawable.placeholder)

                    .into(image);
            title.setText(product.getName());
            Resources res=context.getResources();
            String pr=String.format(res.getString(R.string.price),product.getPrice());
            price.setText(pr);
        }

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context, ProductDetialsActivity.class);
            intent.putExtra("pass_product_id",product_id);
            view.getContext().startActivity(intent);
        }
    }
}
