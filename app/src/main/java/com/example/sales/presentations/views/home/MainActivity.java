package com.example.sales.presentations.views.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.dataResponse.product.ProductResponse;
import com.example.sales.databinding.ActivityMainBinding;
import com.example.sales.presentations.adapter.ProductAdapter;
import com.example.sales.presentations.views.authentications.sign_in.SignInActivity;
import com.example.sales.ultils.AppConstant;
import com.example.sales.ultils.SharePref;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    MainViewModel mainViewModel;
    List<ProductResponse> listProductResponses;
    ProductAdapter productAdapter;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewbiding
        mainBinding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        token=SharePref.getInstance(MainActivity.this).getToken();

        initView();

        addViewModel();

        addEvent(token);

        fetchProDuctStatus();
    }

    private void addEvent(String token) {
        mainViewModel.fetchProduct();

        mainBinding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogOut(token);
            }
        });
    }

    private void onClickLogOut(String token) {
        SharePref.getInstance(MainActivity.this).removeToken(token);
        startActivity(new Intent(MainActivity.this, SignInActivity.class));
        finish();
    }

    private void initView() {
        //Toolbar
        setSupportActionBar(mainBinding.toolbarHome);
        getSupportActionBar().setTitle("Home");
        //Adapter
        listProductResponses = new ArrayList<>();
        productAdapter = new ProductAdapter();
        mainBinding.recyclerViewHome.setAdapter(productAdapter);
    }

    private void addViewModel() {
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MainViewModel(getApplicationContext());
            }
        }).get(MainViewModel.class);
    }

    private void fetchProDuctStatus() {
        mainViewModel.getProduct().observe(this, new Observer<AppResource<List<ProductResponse>>>() {
            @Override
            public void onChanged(AppResource<List<ProductResponse>> listAppResource) {
                switch (listAppResource.status) {
                    case LOADING:
                        Log.d(AppConstant.TAG, "onChanged: LOADING");
                        isShowLoading(true);
                        break;
                    case SUCCESS:
                        Log.d(AppConstant.TAG, "onChanged: SUCCESS " + listAppResource.data.size());
                        productAdapter.updateListProduct(listAppResource.data);
                        isShowLoading(false);
                        break;
                    case ERROR:
                        Toast.makeText(MainActivity.this, listAppResource.message, Toast.LENGTH_SHORT).show();
                        isShowLoading(false);
                        break;
                }
            }
        });
    }

    private void isShowLoading(boolean b) {
        if (b) {
            mainBinding.includeLoading.layoutLoading.setVisibility(View.VISIBLE);
        } else {
            mainBinding.includeLoading.layoutLoading.setVisibility(View.GONE);
        }
    }
}