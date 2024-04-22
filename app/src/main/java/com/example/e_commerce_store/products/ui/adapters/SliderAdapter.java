package com.example.e_commerce_store.products.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.e_commerce_store.R;
import com.example.e_commerce_store.products.data.models.Image;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {

    private final Context context;
    private List<Image> imageList;

    public SliderAdapter(Context context) {
        this.context = context;

    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.slider_row,parent,false);
        return new SliderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        Image image=imageList.get(position);

        Glide.with(context)
                .load(image.getImage())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(viewHolder.imageView);
    }


    @Override
    public int getCount() {
        if (imageList==null)
            return 0;
        return imageList.size();
    }

    public void setImageList(List<Image> imageList){
        this.imageList=imageList;
        notifyDataSetChanged();
    }
    class SliderViewHolder extends SliderViewAdapter.ViewHolder{
         ImageView imageView;
        public SliderViewHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.slider_image);
        }
    }
}
