package com.example.e_commerce_store.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.e_commerce_store.R;

public class QuantityView extends FrameLayout {


    private ImageView subtract;
    private EditText quantity;
    private ImageView add;

    private int minQuantity;
    private int maxQuantity;
    private int startQuantity;
    private int currentQuantity;
    private int deltaQuantity;

    OnButtonClicked listener;

    public interface OnButtonClicked{
        void onClick();
    }

    public void setOnButtonClicked(QuantityView.OnButtonClicked o){
        listener=o;
    }

    public QuantityView(@NonNull Context context) {
        super(context);
        init();
    }


    public QuantityView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        obtainStyledAttributes(context, attrs, 0);
    }

    public QuantityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        obtainStyledAttributes(context, attrs, defStyleAttr);
    }

    private void init() {
        inflate(getContext(), R.layout.custom_view_layout, this);
        subtract = findViewById(R.id.subtract);
        quantity = findViewById(R.id.quantity);
        add = findViewById(R.id.add);
        subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountQuantity(true);
                listener.onClick();
            }
        });
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountQuantity(false);
                listener.onClick();
            }
        });

        setupView();
    }
    private void obtainStyledAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.QuantityView, defStyleAttr, 0);
            minQuantity = typedArray.getInteger(R.styleable.QuantityView_minQuantity, 0);
            maxQuantity = typedArray.getInteger(R.styleable.QuantityView_maxQuantity, 100);
            startQuantity = typedArray.getInteger(R.styleable.QuantityView_startQuantity, 0);
            deltaQuantity = typedArray.getInteger(R.styleable.QuantityView_deltaQuantity, 1);

            return;
        }
        minQuantity = 0;
        maxQuantity = 100;
        startQuantity = 0;
        deltaQuantity = 1;

    }
    private void setupView() {

          add.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.plus));
          subtract.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.minus));

        if (startQuantity <= minQuantity) {
            currentQuantity = minQuantity;

        } else if (startQuantity >= maxQuantity) {
            currentQuantity = maxQuantity;

        } else {
            currentQuantity = startQuantity;

        }
        quantity.setText(Integer.toString(currentQuantity));
    }




    private void setCountQuantity(boolean isSubtract) {
        currentQuantity = isSubtract ? subtractQuantity(currentQuantity) : addQuantity(currentQuantity);
        quantity.setText(Integer.toString(currentQuantity));
    }

    private int subtractQuantity(int currentQuantity) {
        if ((currentQuantity - deltaQuantity) <= minQuantity) {
            currentQuantity = minQuantity;

        } else {
            currentQuantity -= deltaQuantity;

        }
        return currentQuantity;
    }
    private int addQuantity(int currentQuantity) {
        if ((currentQuantity + deltaQuantity) >= maxQuantity) {
            currentQuantity = maxQuantity;

        } else {
            currentQuantity += deltaQuantity;

        }
        return currentQuantity;
    }


    public int getQuantity() {
        return currentQuantity;
    }

    public void setQuantity(int amount){
        this.currentQuantity=amount;
        quantity.setText(String.valueOf(amount));

    }


}
