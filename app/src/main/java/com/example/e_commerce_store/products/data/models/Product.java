package com.example.e_commerce_store.products.data.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {


    private int id;
    private String name;
    private String description;
    private String price;
    private String image;
    private int amount;
    private int category_id;

    @SerializedName("rel__product_size")
    private List<Size> sizeList;
    @SerializedName("rel__product_image")
    private List<Image> imageList;

    public Product(int id, String name, String description, String price, String image, int category_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category_id = category_id;
    }

    public Product(int id, String name, String description, String price, String image, int amount, int category_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.amount = amount;
        this.category_id = category_id;
    }

    public Product(int id, String name, String description, String price, String image, int amount, int category_id, List<Size> sizeList, List<Image> imageList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.amount = amount;
        this.category_id = category_id;
        this.sizeList = sizeList;
        this.imageList = imageList;
    }

    public List<Size> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<Size> sizeList) {
        this.sizeList = sizeList;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }





    public static DiffUtil.ItemCallback<Product> DIFF_CALLBACK = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Product product = (Product) obj;
        return product.id == this.id;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("productid:"+id);
        stringBuilder.append("\n");
        stringBuilder.append("name:"+name);
        stringBuilder.append("\n");
        stringBuilder.append("description:"+description);
        stringBuilder.append("\n");
        stringBuilder.append("price:"+price);
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
