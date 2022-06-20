package com.example.sales.data.responsitories;

import android.content.Context;

import com.example.sales.data.datasource.data_remote.ApiService;
import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.RetrofitClient;
import com.example.sales.data.datasource.data_remote.dataRequest.OrderRequest;
import com.example.sales.data.datasource.data_remote.dataResponse.product.OrderProductRespone;

import retrofit2.Call;

public class OrderProductRespository {
    private ApiService apiService;

    public OrderProductRespository(Context context) {
        apiService= RetrofitClient.getRetrofitClient(context).getApiService();
    }

    public Call<AppResource<OrderProductRespone>> addToCart(OrderRequest orderRequest)
    {
        return apiService.addToCart(orderRequest);
    }



}

