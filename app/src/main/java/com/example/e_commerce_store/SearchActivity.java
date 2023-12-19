package com.example.e_commerce_store;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.TextView;
import com.example.e_commerce_store.adapters.SearchAdapter;
import com.example.e_commerce_store.models.Product;
import com.example.e_commerce_store.utils.Constants;
import com.example.e_commerce_store.utils.SaveToSharedPreferance;
import com.example.e_commerce_store.view.RecyclerViewDecoration;
import com.example.e_commerce_store.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

    SearchViewModel searchViewModel;
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;
    int id;
    String token;
    List<Product> products;
    TextView noResults;
    ArrayList<String> array;
    SearchView searchView;
    CursorAdapter suggestionAdapter;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        noResults= findViewById(R.id.no_results);
        searchViewModel=new ViewModelProvider(this).get(SearchViewModel.class);
        token= SaveToSharedPreferance.getToken(this);
        products=new ArrayList<>();
        recyclerView=findViewById(R.id.search_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(this,R.drawable.divider));
        recyclerView.setLayoutManager(layoutManager);
        searchAdapter=new SearchAdapter(this,products);
        recyclerView.setAdapter(searchAdapter);

        array=new ArrayList<>();
        suggestionAdapter= new SimpleCursorAdapter(this,R.layout.search_layout,
                null,new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{R.id.text1},
                0);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action=intent.getAction();
        if (action.equals(Intent.ACTION_SEARCH)){
            String mQuery=intent.getStringExtra(SearchManager.QUERY);

            id=intent.getIntExtra("cat_id",1);
            doSearch(id,mQuery);
        }

    }

    void doSearch(int id,String query){
        query= "%"+query+"%";
        searchViewModel.getSearchResults(id,query, Constants.API_KEY,"Bearer "+token).observe(this, searchResults -> {
            if (searchResults!=null){
               products= searchResults.getProductList();
               if (products.isEmpty())
                   noResults.setVisibility(View.VISIBLE);
               else
                   noResults.setVisibility(View.GONE);
               searchAdapter.submitList(products);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem cart=menu.findItem(R.id.cart);
        cart.setVisible(false);
        searchView=(SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setSuggestionsAdapter(suggestionAdapter);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);

        return super.onCreateOptionsMenu(menu);

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
        countDownTimer=new CountDownTimer(500,500) {
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
    private void getSuggestions(String newText) {
        newText="%"+newText+"%";
        searchViewModel.getSearchSuggestions(id,newText, Constants.API_KEY,"Bearer "+token).observe(this, searchResults -> {
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
        });
    }
}
