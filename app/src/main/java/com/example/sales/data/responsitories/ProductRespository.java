package com.example.sales.data.responsitories;

import android.content.Context;

import com.example.sales.data.datasource.data_remote.ApiService;
import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.dataResponse.product.ProductResponse;
import com.example.sales.data.datasource.data_remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;

public class ProductRespository {
    private ApiService apiService;

    public ProductRespository(Context context) {
        apiService = RetrofitClient.getRetrofitClient(context).getApiService();
    }

    public Call<AppResource<List<ProductResponse>>> fetchProduct() {
        return apiService.FetchProduct();
    }
}
