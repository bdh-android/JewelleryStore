package com.example.e_commerce_store;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


import com.example.e_commerce_store.adapters.ProductListAdapter;
import com.example.e_commerce_store.models.Product;
import com.example.e_commerce_store.models.SearchResults;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.NetworkState;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.viewmodel.ProductViewModel;
import com.example.e_commerce_store.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseActivity implements androidx.appcompat.widget.SearchView.OnQueryTextListener,
        SearchView.OnSuggestionListener {


    private ProductListAdapter adapter;
    private ProductViewModel productViewModel;
    private SearchViewModel searchViewModel;
    RecyclerView recyclerView;
    int id;
    String token;
    ArrayList<String> array;
    SearchView searchView;
    CursorAdapter suggestionAdapter;
    CountDownTimer countDownTimer;
    ProgressBar loadingProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle=getIntent().getExtras();
          id=bundle.getInt("cat_id");
          token=getTokenFromStorage();

        recyclerView=findViewById(R.id.product_recyclerview);
        loadingProgress=findViewById(R.id.loading_progress);

        productViewModel= new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T)new ProductViewModel(id,token);
            }
        }).get(ProductViewModel.class);

        searchViewModel=new ViewModelProvider(this).get(SearchViewModel.class);

        GridLayoutManager layoutManager = new GridLayoutManager(ProductActivity.this,2);

         adapter=new ProductListAdapter(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);


        productViewModel.getProductsLiveData().observe(this, pagedList -> {

            adapter.submitList(pagedList);
        });


        productViewModel.getNetworkState().observe(this, networkState -> {

            if (networkState== NetworkState.LOADED){
                loadingProgress.setVisibility(View.GONE);
            }else if (networkState== NetworkState.LOADING) {
                loadingProgress.setVisibility(View.VISIBLE);
            }else   loadingProgress.setVisibility(View.GONE);

        });


        recyclerView.setAdapter(adapter);
         array=new ArrayList<>();
         suggestionAdapter= new SimpleCursorAdapter(this,R.layout.search_layout,
                 null,new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                 new int[]{R.id.text1},
                 0);
    }

    private String getTokenFromStorage(){
        return SaveToSharedPreferance.getToken(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
          searchView=(SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setSuggestionsAdapter(suggestionAdapter);

        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cart) {
             startActivity(new Intent(ProductActivity.this,CartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

     getResults(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (countDownTimer!=null)
            countDownTimer.cancel();
        countDownTimer=new CountDownTimer(200,200) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                getSuggestions(newText);
            }
        };
        countDownTimer.start();

        return true;
    }

    private void getSuggestions(String newText) {
        newText="%"+newText+"%";
        searchViewModel.getSearchSuggestions(id,newText, Constants.API_KEY,"Bearer "+token).observe(this, new Observer<SearchResults>() {
            @Override
            public void onChanged(SearchResults searchResults) {
                if (searchResults!=null){
                    array.clear();
                    List<Product> productList=searchResults.getProductList();
                    for (int i=0;i<productList.size();i++){
                        array.add(productList.get(i).getName());
                    }
                    String[] colums={BaseColumns._ID,
                            SearchManager.SUGGEST_COLUMN_TEXT_1,
                    SearchManager.SUGGEST_COLUMN_INTENT_DATA};
                    MatrixCursor cursor=new MatrixCursor(colums);
                    for (int i=0;i<array.size();i++){
                        String[] tmp={Integer.toString(i),
                        array.get(i),array.get(i)};
                        cursor.addRow(tmp);
                    }
                    suggestionAdapter.swapCursor(cursor);
                }

            }
        });
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        String se=array.get(position);
        getResults(se);
        return true;
    }


    void getResults(String query)
    {
        Intent intent=new Intent(searchView.getContext(),SearchActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY,query);
        intent.putExtra("cat_id",id);
        startActivity(intent);
        searchView.clearFocus();
    }
}
