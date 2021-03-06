package com.example.sales.presentations.views.cart_history;

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
import com.example.sales.data.datasource.data_remote.dataResponse.product.OrderProductRespone;
import com.example.sales.data.datasource.data_remote.dataResponse.product.ProductResponse;
import com.example.sales.databinding.ActivityCartBinding;
import com.example.sales.presentations.adapter.CartAdapter;
import com.example.sales.presentations.views.authentications.sign_in.SignInActivity;
import com.example.sales.presentations.views.home.MainActivity;
import com.example.sales.ultils.AppBinding;
import com.example.sales.ultils.AppConstant;
import com.example.sales.ultils.SharePref;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    Intent intent;
    ActivityCartBinding mBinding;
    List<ProductResponse> listProductResponses;
    CartAdapter mCartAdapter;
    OrderProductRespone mOrder;
    String token;
    CartViewModel mCartViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


        initData();
        initView();
        observerData();
        event();
    }

    private void event() {
        mCartViewModel.fetchCart(mOrder.getId());

        mCartAdapter.setOnListenerCartItem(new CartAdapter.setOnclickCartItem() {
            @Override
            public void onPlus(int postion) {
                int countQuanlity=mOrder.getProducts().get(postion).getQuantity()+1;
                mCartViewModel.updateCart(mOrder.getProducts().get(postion).getId(),mOrder.getId(), countQuanlity);
            }

            @Override
            public void onMinus(int postion) {
                if (listProductResponses.get(postion).getQuantity() > 1) {
                    int countQuanlity=mOrder.getProducts().get(postion).getQuantity()-1;
                    mCartViewModel.updateCart(mOrder.getProducts().get(postion).getId(),mOrder.getId(), countQuanlity);
                }
            }

            @Override
            public void onDelete(int postion) {

            }
        });

        mBinding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickConfirm();
            }
        });
    }

    private void onClickConfirm() {
        mOrder.setStatus(true);
        mCartViewModel.fetchConfirm(mOrder.getId(),mOrder.isStatus());
    }

    private void observerData() {
        mCartViewModel.getCart().observe(this, orderProductResponeAppResource -> {
            switch (orderProductResponeAppResource.status) {
                case LOADING:
                    isShowLoading(true);
                    break;
                case SUCCESS:
                    isShowLoading(false);
                    break;
                case ERROR:
                    isShowLoading(false);
                    break;
            }
        });

        mCartViewModel.getDataUpdate().observe(this, orderProductResponeAppResource -> {
            switch (orderProductResponeAppResource.status) {
                case LOADING:
                    isShowLoading(true);
                    break;
                case SUCCESS:
                    Log.d(AppConstant.TAG, "onChanged: "+ orderProductResponeAppResource.data.getPrice());
                    mOrder=orderProductResponeAppResource.data;
                    mCartAdapter.updateCart(mOrder.getProducts());
                    mBinding.textviewTotalAmount.setText("T???ng ti???n: " + AppBinding.setPrice(mOrder.getPrice()) + " VN??");
                    isShowLoading(false);
                    break;
                case ERROR:
                    isShowLoading(false);
                    break;
            }
        });

        mCartViewModel.getConfirm().observe(this, new Observer<AppResource<String>>() {
            @Override
            public void onChanged(AppResource<String> stringAppResource) {
                switch (stringAppResource.status) {
                    case LOADING:
                        isShowLoading(true);
                        break;
                    case SUCCESS:
                        Toast.makeText(CartActivity.this, "Da thanh toan thanh cong", Toast.LENGTH_SHORT).show();
                        mOrder=null;
                        Intent intent= new Intent(CartActivity.this, SignInActivity.class);
                        SharePref.getInstance(getApplicationContext()).removeToken(token);
                        startActivity(intent);
//                        intent.putExtra(AppConstant.KEY_CARTPRODUCT,mOrder);
//                        setResult(RESULT_OK, intent);
                        finish();
                        isShowLoading(false);
                        break;
                    case ERROR:
                        Log.d(AppConstant.TAG, "onChanged: " + stringAppResource.data);
                        isShowLoading(false);
                        break;
                }
            }
        });
    }

    private void initData() {

        //init list cart
        listProductResponses = new ArrayList<>();
        //get data from mainactivity
        intent = getIntent();
        mOrder = (OrderProductRespone) intent.getSerializableExtra(AppConstant.KEY_CARTPRODUCT);
        //get tolen
        token = SharePref.getInstance(CartActivity.this).getToken();
        //set list Cart
        listProductResponses = mOrder.getProducts();
        //set toltal
        mBinding.textviewTotalAmount.setText("T???ng ti???n: " + AppBinding.setPrice(mOrder.getPrice()) + " VN??");
        //add cart model
        mCartViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new CartViewModel(getApplicationContext());
            }
        }).get(CartViewModel.class);

        Log.d(AppConstant.TAG, "initData: " + mOrder.getId() +" "+token);
    }

    private void initView() {
        //set Toolbar
        setSupportActionBar(mBinding.toolbarCart);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cart");
        //set adapter
        mCartAdapter = new CartAdapter();
        mCartAdapter.updateCart(listProductResponses);
        mBinding.recyclerView.setAdapter(mCartAdapter);
    }

    private void isShowLoading(boolean b) {
        if (b) {
            mBinding.includeLoading.layoutLoading.setVisibility(View.VISIBLE);
        } else {
            mBinding.includeLoading.layoutLoading.setVisibility(View.GONE);
        }
    }
}