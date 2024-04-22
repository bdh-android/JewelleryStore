package com.example.e_commerce_store.products.ui;

import static com.google.android.material.shape.CornerFamily.CUT;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_commerce_store.MainActivity;
import com.example.e_commerce_store.R;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.products.data.models.Image;
import com.example.e_commerce_store.products.data.models.Product;
import com.example.e_commerce_store.products.data.models.Size;
import com.example.e_commerce_store.products.ui.adapters.SliderAdapter;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetialsFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    TextView title, description, price, chooseSize;
    Button                  addToCart;
    LinearLayout            sizes;
    SliderView              imageSlider;
    ProductDetailsViewModel productDetailsViewModel;
    int                     prod_id;
    List<Size>              productSizes;
    List<Image>             productImages;
    String                  itemViewSize;
    String                  name, pric, image;
    SliderAdapter      sliderAdapter;
    SwipeRefreshLayout refreshLayout;
    ProgressBar        progressBar;
    int                product_id;
    boolean            lock        = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detials,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        product_id = getArguments().getInt("pass_product_id");
        //token = SaveToSharedPreferance.getToken(getActivity());

        title = view.findViewById(R.id.product_detail_title);
        description = view.findViewById(R.id.product_detail_desc);
        price = view.findViewById(R.id.product_detail_price);
        addToCart = view.findViewById(R.id.add_to_cart);
        imageSlider = view.findViewById(R.id.imageslider);
        sizes = view.findViewById(R.id.sizes_layout);
        chooseSize = view.findViewById(R.id.sizes);
        progressBar = view.findViewById(R.id.progressbar);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);


        sliderAdapter = new SliderAdapter(getActivity());
        imageSlider.setSliderAdapter(sliderAdapter);

        productDetailsViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);

        productSizes = new ArrayList<>();
        productImages = new ArrayList<>();
        addToCart.setEnabled(false);


        getProduct();

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int userID = SaveToSharedPreferance.getUserId(getActivity());
                String size = itemViewSize == null ? String.valueOf(0) : itemViewSize;

                productDetailsViewModel.saveToCart( prod_id, 1, size, name, pric, image);
                Toast.makeText(getActivity(), "Product is added to your local cart.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProduct() {
        productDetailsViewModel.getProductDetails(product_id)
                .observe(getViewLifecycleOwner(), new Observer<Result<Product>>() {
                    @Override
                    public void onChanged(Result<Product> productDetail) {
                        if (productDetail != null) {
                            if (productDetail.getStatus()) {
                                if (productDetail.getData().getSizeList() != null)
                                    productSizes = productDetail.getData().getSizeList();
                                if (productDetail.getData().getImageList() != null && !productDetail.getData().getImageList().isEmpty()) {
                                    productImages = productDetail.getData().getImageList();
                                    sliderAdapter.setImageList(productImages);
                                } else {
                                    //if there are not any images load default

                                    productImages.add(new Image(0, 0, "http://localhost:8000/storage/images/slider1.jpeg"));
                                    productImages.add(new Image(0, 0, "http://localhost:8000/storage/images/slider3.jpg"));
                                    productImages.add(new Image(0, 0, "http://localhost.3:8000/storage/images/slider4.jpg"));
                                    productImages.add(new Image(0, 0, "http://localhost.3:8000/storage/images/slider5.jpg"));
                                    sliderAdapter.setImageList(productImages);
                                }


                                name = productDetail.getData().getName();
                                String desc = productDetail.getData().getDescription();
                                pric = productDetail.getData().getPrice();
                                prod_id = productDetail.getData().getId();
                                image = productDetail.getData().getImage();
                                setData(name, desc, pric);


                                if (sizes.getChildCount() > 0) {
                                    sizes.removeAllViews();
                                }

                                if (productSizes != null && !productSizes.isEmpty()) {
                                    chooseSize.setText("Choose your size:");
                                    for (int i = 0; i < productSizes.size(); i++) {
                                        String s = productSizes.get(i).getSize();
                                        createButtons(s);
                                    }
                                } else {
                                    addToCart.setEnabled(true);
                                    chooseSize.setVisibility(View.INVISIBLE);
                                }

                                progressBar.setVisibility(View.INVISIBLE);
                                if (refreshLayout.isRefreshing() && !lock)
                                    refreshLayout.setRefreshing(false);
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                ((MainActivity)getActivity()).ShowSnackBarMsg(productDetail.getMessage(), android.R.color.holo_red_light);
                                lock = true;
                                if (refreshLayout.isRefreshing())
                                    refreshLayout.setRefreshing(false);
                            }
                        }
                    }
                });


    }

    private void setData(String n, String d, String p) {

        title.setText(n);
        String string = "";
        if (!d.equals(""))
            string = "Product Description:" +
                    "\n" +
                    "\n" +
                    d;
        description.setText(string);
        Resources res = getResources();
        String pr = String.format(res.getString(R.string.price), p);
        price.setText(pr);

    }

    //create buttons for product sizes
    void createButtons(String s) {
        Button button = new Button(getActivity());
        sizes.setWeightSum(productSizes.size());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        params.rightMargin = (int) getResources().getDimension(R.dimen.size_buttons_margin);
        button.setLayoutParams(params);

        button.setBackground(setSizeButtonsBackground(R.color.colorPrimaryDark));

        button.setText(s);
        sizes.addView(button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < sizes.getChildCount(); i++) {
            sizes.getChildAt(i).setBackground(setSizeButtonsBackground(R.color.colorPrimaryDark));

        }

        Button button = (Button) view;

        itemViewSize = button.getText().toString();
        button.setBackground(setSizeButtonsBackground(R.color.red));

        Toast.makeText(getActivity(), itemViewSize, Toast.LENGTH_SHORT).show();
        addToCart.setEnabled(true);
    }

    private MaterialShapeDrawable setSizeButtonsBackground(int color) {
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder();
        builder.setAllCorners(CUT, 40f);
        ShapeAppearanceModel shapeAppearanceModel = builder.build();
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
        materialShapeDrawable.setStroke(5f, getResources().getColor(color));
        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.transparent));
        materialShapeDrawable.setFillColor(colorStateList);

        return materialShapeDrawable;
    }

    @Override
    public void onRefresh() {

        getProduct();
        lock = false;

    }

}
