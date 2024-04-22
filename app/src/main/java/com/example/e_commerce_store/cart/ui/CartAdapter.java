package com.example.e_commerce_store.cart.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce_store.R;
import com.example.e_commerce_store.core.models.CartItem;
import com.example.e_commerce_store.core.view.QuantityView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    public interface OnQuantityChangedListener{
        void onQuantityChanged(int position,int Quantity);
        void onRemoveItem(int position);
        void onRestoreItem(CartItem item,int position);
    }
    private List<CartItem> cartItemList;
    private final Context context;
    OnQuantityChangedListener onQuantityChangedListener;
    public CartAdapter(Context context,OnQuantityChangedListener onQuantityChangedListener) {
        this.context = context;
        this.onQuantityChangedListener=onQuantityChangedListener;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_row,parent,false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(cartItemList.get(position));
    }

    @Override
    public int getItemCount() {
        if (cartItemList==null)
        return 0;
        return cartItemList.size();
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , QuantityView.OnButtonClicked {

        ImageView productImage;

        TextView productTitle,productPrice;
        CartItem cartItem;
        QuantityView quantityControl;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.cart_item_image);
            productTitle=itemView.findViewById(R.id.cart_item_name);
            productPrice=itemView.findViewById(R.id.cart_item_price);

            quantityControl=itemView.findViewById(R.id.quantity1);
            quantityControl.setOnButtonClicked(this);


        }

        void bind(CartItem cartItem) {
            this.cartItem=cartItem;
            Glide.with(context)
                    .load(cartItem.getImage())
                    .placeholder(R.drawable.placeholder)
                    .into(productImage);

            quantityControl.setQuantity(cartItem.getAmount());
            productTitle.setText(cartItem.getName());
            double Price=0;
            try {
                  Price = Double.parseDouble(cartItem.getPrice())* quantityControl.getQuantity();
            }catch (NumberFormatException ex){
                Log.d("bind: ",ex.getMessage());
            }

            productPrice.setText( "$."+ Price);

        }

        @Override
        public void onClick(View view) {
            int id=view.getId();


            switch (id){


            }
        }

        @Override
        public void onClick() {

            int q=quantityControl.getQuantity();

            cartItemList.get(getAdapterPosition()).setAmount(q);

            onQuantityChangedListener.onQuantityChanged(getAdapterPosition(),q);
            double Price=0;
            try {
                Price = Double.parseDouble(cartItem.getPrice())* quantityControl.getQuantity();
            }catch (NumberFormatException ex){
                Log.d("bind: ",ex.getMessage());
            }

            productPrice.setText("$."+ Price);

        }
    }


    public void removeItem(int position) {
        onQuantityChangedListener.onRemoveItem(position);

        notifyItemRemoved(position);

    }

    public void restoreItem(CartItem item, int position) {
        onQuantityChangedListener.onRestoreItem(item,position);

        notifyItemInserted(position);

    }

    public List<CartItem> getData() {
        return cartItemList;
    }
}
