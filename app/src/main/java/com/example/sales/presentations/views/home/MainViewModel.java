package com.example.sales.presentations.views.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.dataResponse.product.ProductResponse;
import com.example.sales.data.responsitories.ProductRespository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private ProductRespository productRespository;
    private MutableLiveData<AppResource<List<ProductResponse>>> product = new MutableLiveData<>();

    public MainViewModel(Context context) {
        productRespository = new ProductRespository(context);
    }

    public LiveData<AppResource<List<ProductResponse>>> getProduct() {
        return product;
    }

    public void fetchProduct() {
        product.setValue(new AppResource.Loading<>(null));
        Call<AppResource<List<ProductResponse>>> callProduct = productRespository.fetchProduct();
        callProduct.enqueue(new Callback<AppResource<List<ProductResponse>>>() {
            @Override
            public void onResponse(Call<AppResource<List<ProductResponse>>> call, Response<AppResource<List<ProductResponse>>> response) {
                if (response.isSuccessful()) {
                    AppResource<List<ProductResponse>> productData = response.body();
                    if (productData.data != null) {
                        product.setValue(new AppResource.Success<>(productData.data));
                    }
                } else {
                    try {
                        JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
                        String message = jsonObjectError.getString("message");
                        product.setValue(new AppResource.Error<>(message));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<List<ProductResponse>>> call, Throwable t) {
                product.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

}
