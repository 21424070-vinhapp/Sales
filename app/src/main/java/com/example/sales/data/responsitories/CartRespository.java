package com.example.sales.data.responsitories;

import android.content.Context;

import com.example.sales.data.datasource.data_remote.ApiService;
import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.RetrofitClient;
import com.example.sales.data.datasource.data_remote.dataRequest.ConfirmRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.IdOrderRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.OrderRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.UpdateCartRequest;
import com.example.sales.data.datasource.data_remote.dataResponse.product.OrderProductRespone;

import retrofit2.Call;

public class CartRespository {
    private ApiService apiService;

    public CartRespository(Context context) {
        apiService = RetrofitClient.getRetrofitClient(context).getApiService();
    }

    public Call<AppResource<OrderProductRespone>> fetchCart(IdOrderRequest idOrderRequest) {
        return apiService.fetchCart(idOrderRequest);
    }

    public Call<AppResource<OrderProductRespone>> updateCart(UpdateCartRequest updateCartRequest)
    {
        return apiService.updateCart(updateCartRequest);
    }

    public Call<AppResource<String>> confirm(ConfirmRequest confirmRequest)
    {
        return apiService.confirm(confirmRequest);
    }

}
