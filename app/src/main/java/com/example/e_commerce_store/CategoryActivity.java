package com.example.e_commerce_store;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_commerce_store.adapters.CategoryAdapter;
import com.example.e_commerce_store.models.Category;
import com.example.e_commerce_store.models.CategoryResponse;
import com.example.e_commerce_store.models.LoginResponse;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.viewmodel.CategoryViewModel;
import com.example.e_commerce_store.viewmodel.LoginViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    CategoryViewModel categoryViewModel;
    LoginViewModel    loginViewModel;
    String            token;
    CategoryAdapter   parentItemAdapter;
    List<Category>    arrayList;
    private Menu menu;
    ProgressBar progressBar;
    boolean     isConnected = false;
    boolean     lock        = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        Bundle b = getIntent().getExtras();
        token = b.getString("token");
        arrayList = new ArrayList<>();

        progressBar = findViewById(R.id.progressbar);
        RecyclerView ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CategoryActivity.this);

        parentItemAdapter = new CategoryAdapter(arrayList, this);


        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);

        if (isConnected)
            categoriesList();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.reset_password) {
            startActivity(new Intent(CategoryActivity.this, PasswordActivity.class));

        } else if (id == R.id.order) {
            startActivity(new Intent(CategoryActivity.this, OrdersActivity.class));
        } else if (id == R.id.cart) {
            startActivity(new Intent(CategoryActivity.this, CartActivity.class));
        } else if (id == R.id.log_out) {
            loginViewModel.logout(Constants.API_KEY, "Bearer " + token).observe(this, new Observer<LoginResponse>() {
                @Override
                public void onChanged(LoginResponse loginResponse) {
                    if (loginResponse != null) {
                        if (loginResponse.isStatus()) {
                            String msg = loginResponse.getMsg();
                            Toast.makeText(CategoryActivity.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CategoryActivity.this, SignInActivity.class));
                            finish();
                        } else {
                            Toast.makeText(CategoryActivity.this, loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void categoriesList() {


        categoryViewModel.getCategory(Constants.API_KEY, "Bearer " + token).observe(CategoryActivity.this, new Observer<CategoryResponse>() {
            @Override
            public void onChanged(CategoryResponse categoryResponse) {

                if (categoryResponse != null) {
                    if (categoryResponse.isStatus()) {

                        parentItemAdapter.submitCategoryList(categoryResponse.getCategoryList());
                        parentItemAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {

                        ShowSnackBarMsg(categoryResponse.getMsg(), android.R.color.holo_red_light);
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                }
            }
        });

    }


    @Override
    public void onChanged(boolean isConn) {
        super.onChanged(isConn);
        isConnected = isConn;
        parentItemAdapter.isConnectedToNetwork(isConn);
        if (isConnected && lock) {
            categoriesList();
            lock = false;
        }
    }
}
