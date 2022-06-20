package com.example.sales.presentations.views.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sales.R;
import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.dataResponse.product.OrderProductRespone;
import com.example.sales.data.datasource.data_remote.dataResponse.product.ProductResponse;
import com.example.sales.databinding.ActivityMainBinding;
import com.example.sales.presentations.adapter.ProductAdapter;
import com.example.sales.presentations.views.authentications.sign_in.SignInActivity;
import com.example.sales.presentations.views.cart_history.CartActivity;
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
    TextView mTvTotalCart;
    OrderProductRespone order;
    int quantities = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewbiding
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        token = SharePref.getInstance(MainActivity.this).getToken();

        initView();

        addViewModel();

        addEvent(token);

        fetchProDuctStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem menuItem = menu.findItem(R.id.action_cart);
        View viewMenu = menuItem.getActionView();
        mTvTotalCart = viewMenu.findViewById(R.id.text_cart_badge);
        quantities = getQualities(order == null ? null : order.getProducts());
        setTotalCount(quantities);
        viewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                if (order != null) {
                    Bundle b = new Bundle();
                    b.putSerializable(AppConstant.KEY_CARTPRODUCT, order);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTotalCount(int totalCount) {
        if (totalCount == 0) {
            mTvTotalCart.setVisibility(View.GONE);
        } else {
            mTvTotalCart.setVisibility(View.VISIBLE);
            mTvTotalCart.setText(String.valueOf(Math.min(totalCount, 99)));
        }
    }

    private void addEvent(String token) {
        mainViewModel.fetchProduct();

        productAdapter.setOnItemClickProduct(new ProductAdapter.OnItemClickProduct() {
            @Override
            public void onClick(int position) {
                mainViewModel.fetchOrder(productAdapter.getListFoods().get(position).getId());
            }
        });

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
        //get list product
        mainViewModel.getProduct().observe(this, new Observer<AppResource<List<ProductResponse>>>() {
            @Override
            public void onChanged(AppResource<List<ProductResponse>> listAppResource) {
                switch (listAppResource.status) {
                    case LOADING:
                        //Log.d(AppConstant.TAG, "onChanged: LOADING");
                        isShowLoading(true);
                        break;
                    case SUCCESS:
                        //Log.d(AppConstant.TAG, "onChanged: SUCCESS " + listAppResource.data.size());
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

        //add to card
        mainViewModel.addToCart().observe(this, new Observer<AppResource<OrderProductRespone>>() {
            @Override
            public void onChanged(AppResource<OrderProductRespone> orderProductResponeAppResource) {
                switch (orderProductResponeAppResource.status) {
                    case LOADING:
                        //Log.d(AppConstant.TAG, "onChanged: LOADING");
                        isShowLoading(true);
                        break;
                    case SUCCESS:
                        order = orderProductResponeAppResource.data;
                        quantities = getQualities(order == null ? null : order.getProducts());
                        setTotalCount(quantities);
                        isShowLoading(false);
                        break;
                    case ERROR:
                        isShowLoading(false);
                        break;
                }
            }
        });

        mainViewModel.getCart().observe(this, new Observer<AppResource<OrderProductRespone>>() {
            @Override
            public void onChanged(AppResource<OrderProductRespone> orderProductResponeAppResource) {
                switch (orderProductResponeAppResource.status) {
                    case LOADING:
                        //Log.d(AppConstant.TAG, "onChanged: LOADING");
                        isShowLoading(true);
                        break;
                    case SUCCESS:
                        order = orderProductResponeAppResource.data;
                        quantities = getQualities(order == null ? null : order.getProducts());
                        setTotalCount(quantities);
                        isShowLoading(false);
                        break;
                    case ERROR:
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

    private int getQualities(List<ProductResponse> list) {
        if (list == null) {
            return 0;
        }
        int totalQuantities = 0;
        for (ProductResponse food : list) {
            totalQuantities += food.getQuantity();
        }
        return totalQuantities;
    }

}