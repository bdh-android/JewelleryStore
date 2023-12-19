package com.example.e_commerce_store;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce_store.adapters.SliderAdapter;
import com.example.e_commerce_store.models.CartItem;
import com.example.e_commerce_store.models.Image;
import com.example.e_commerce_store.models.ProductDetail;
import com.example.e_commerce_store.models.Size;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.viewmodel.ProductDetailsViewModel;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.material.shape.CornerFamily.CUT;

public class ProductDetialsActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

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
    String             token;
    int                product_id;
    boolean            lock        = true;
    boolean            isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detials);

        Bundle b = getIntent().getExtras();
        product_id = b.getInt("pass_product_id");

        token = SaveToSharedPreferance.getToken(this);

        title = findViewById(R.id.product_detail_title);
        description = findViewById(R.id.product_detail_desc);
        price = findViewById(R.id.product_detail_price);
        addToCart = findViewById(R.id.add_to_cart);
        imageSlider = findViewById(R.id.imageslider);
        sizes = findViewById(R.id.sizes_layout);
        chooseSize = findViewById(R.id.sizes);
        progressBar = findViewById(R.id.progressbar);
        refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);


        sliderAdapter = new SliderAdapter(this);
        imageSlider.setSliderAdapter(sliderAdapter);

        productDetailsViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);

        productSizes = new ArrayList<>();
        productImages = new ArrayList<>();
        addToCart.setEnabled(false);

        if (isConnected)
            getProduct();

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userID = SaveToSharedPreferance.getUserId(ProductDetialsActivity.this);
                String size = itemViewSize == null ? String.valueOf(0) : itemViewSize;

                SaveToSharedPreferance.saveToCart(ProductDetialsActivity.this, new CartItem(userID, prod_id, 1, size, name, pric, image));
                Toast.makeText(ProductDetialsActivity.this, "Product is added to your local cart.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProduct() {
        productDetailsViewModel.getProductDetails(product_id, Constants.API_KEY, "Bearer " + token).observe(this, new Observer<ProductDetail>() {
            @Override
            public void onChanged(ProductDetail productDetail) {
                if (productDetail != null) {
                    if (productDetail.isStatus()) {
                        if (productDetail.getProduct().getSizeList() != null)
                            productSizes = productDetail.getProduct().getSizeList();
                        if (productDetail.getProduct().getImageList() != null && !productDetail.getProduct().getImageList().isEmpty()) {
                            productImages = productDetail.getProduct().getImageList();
                            sliderAdapter.setImageList(productImages);
                        } else {
                            //if there are not any images load default

                            productImages.add(new Image(0, 0, "http://localhost:8000/storage/images/slider2.jpeg"));
                            productImages.add(new Image(0, 0, "http://localhost:8000/storage/images/slider3.jpg"));

                            productImages.add(new Image(0, 0, "http://localhost.3:8000/storage/images/slider5.jpg"));
                            sliderAdapter.setImageList(productImages);
                        }


                        name = productDetail.getProduct().getName();
                        String desc = productDetail.getProduct().getDescription();
                        pric = productDetail.getProduct().getPrice();
                        prod_id = productDetail.getProduct().getId();
                        image = productDetail.getProduct().getImage();
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
                        ShowSnackBarMsg(productDetail.getMsg(), android.R.color.holo_red_light);
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
        Button button = new Button(this);
        sizes.setWeightSum(productSizes.size());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        params.rightMargin = (int) getResources().getDimension(R.dimen.size_buttons_margin);
        button.setLayoutParams(params);

        button.setBackground(setSizeButtonsBackground(R.color.blue));

        button.setText(s);
        sizes.addView(button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < sizes.getChildCount(); i++) {
            sizes.getChildAt(i).setBackground(setSizeButtonsBackground(R.color.blue));

        }

        Button button = (Button) view;

        itemViewSize = button.getText().toString();
        button.setBackground(setSizeButtonsBackground(R.color.red));

        Toast.makeText(this, itemViewSize, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onChanged(boolean isConn) {
        super.onChanged(isConn);
        isConnected = isConn;
        if (isConnected && lock) {
            getProduct();
            lock = false;
        }

    }
}
