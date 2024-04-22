package com.example.e_commerce_store.products.ui;

import android.app.SearchManager;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_store.MainActivity;
import com.example.e_commerce_store.R;
import com.example.e_commerce_store.core.models.Result;
import com.example.e_commerce_store.core.utils.NetworkState;
import com.example.e_commerce_store.products.data.models.Product;
import com.example.e_commerce_store.products.ui.adapters.ProductListAdapter;
import com.example.e_commerce_store.products.ui.adapters.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductFragment extends Fragment implements androidx.appcompat.widget.SearchView.OnQueryTextListener,
        SearchView.OnSuggestionListener {


    private ProductListAdapter adapter;
    private ProductViewModel productViewModel;
    private RecyclerView recyclerView;
    private int id;
    private ArrayList<String> array;
    private SearchView searchView;
    private CursorAdapter suggestionAdapter;
    private SearchAdapter searchAdapter;
    private CountDownTimer countDownTimer;
    private ProgressBar loadingProgress;
    private ProgressBar searchProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar =view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.search_menu);





        id = getArguments().getInt("cat_id");
        recyclerView=view.findViewById(R.id.product_recyclerview);
        loadingProgress=view.findViewById(R.id.loading_progress);
        searchProgress=view.findViewById(R.id.suggestion_progress);


        productViewModel= new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.setCategoryId(id);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        adapter=new ProductListAdapter(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        /*productViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), pagedList -> {
            adapter.submitList(pagedList);
        });*/
        productViewModel.productLiveData.observe(getViewLifecycleOwner(), pagedList -> {
            adapter.submitList(pagedList);
        });

        productViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {

            if (networkState== NetworkState.LOADED){
                loadingProgress.setVisibility(View.GONE);
            }else if (networkState== NetworkState.LOADING) {
                loadingProgress.setVisibility(View.VISIBLE);
            }else   loadingProgress.setVisibility(View.GONE);

        });


        array=new ArrayList<>();
        suggestionAdapter= new SimpleCursorAdapter(getActivity(),R.layout.search_layout,
                null,new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{R.id.text1},
                0);
        searchAdapter = new SearchAdapter(getActivity(),new ArrayList<Product>());
        searchView = (SearchView)toolbar.getMenu().findItem(R.id.search).getActionView();
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);
        searchView.setSuggestionsAdapter(suggestionAdapter);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cart) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        getResults(query);
        searchProgress.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.equals("")) {
            recyclerView.setAdapter(adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
            recyclerView.setLayoutManager(layoutManager);
        }
        if (countDownTimer!=null)
            countDownTimer.cancel();
        countDownTimer=new CountDownTimer(500,200) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (!newText.equals(""))  getSuggestions(newText);
            }
        };
        countDownTimer.start();

        return true;
    }

    private void getSuggestions(String newText) {
        newText="%"+newText+"%";
        productViewModel.getSearchSuggestions(id,newText).observe(this, new Observer<Result<List<Product>>>() {
            @Override
            public void onChanged(Result<List<Product>> searchResults) {

                if (searchResults!=null){
                    array.clear();
                    List<Product> productList=searchResults.getData();
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
        searchProgress.setVisibility(View.VISIBLE);
        return true;
    }


    void getResults(String query)
    {
        query= "%"+query+"%";
        productViewModel.getSearchResults(id,query).observe(getViewLifecycleOwner(), searchResults -> {
            searchProgress.setVisibility(View.INVISIBLE);
            if (searchResults.getStatus()){
                List<Product> products = searchResults.getData();
                searchAdapter.submitList(products);
                recyclerView.setAdapter(searchAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }else{
                if(!searchResults.getMessage().equals(""))
                    ((MainActivity)requireActivity()).ShowSnackBarMsg(searchResults.getMessage(),android.R.color.holo_red_light);
            }
        });
        searchView.clearFocus();
    }
}
