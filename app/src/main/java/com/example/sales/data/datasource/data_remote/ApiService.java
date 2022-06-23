package com.example.sales.data.datasource.data_remote;

import com.example.sales.data.datasource.data_remote.dataRequest.ConfirmRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.IdOrderRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.OrderRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.UpdateCartRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.UserRequest;
import com.example.sales.data.datasource.data_remote.dataResponse.product.OrderProductRespone;
import com.example.sales.data.datasource.data_remote.dataResponse.product.ProductResponse;
import com.example.sales.data.datasource.data_remote.dataResponse.user.UserRespone;
import com.example.sales.data.responsitories.OrderProductRespository;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/sign-in")
    Call<AppResource<UserRespone>> SignIn(@Body UserRequest userRequest);

    @POST("user/sign-up")
    Call<AppResource<UserRespone>> SignUp(@Body UserRequest userRequest);

    @GET("product")
    Call<AppResource<List<ProductResponse>>> FetchProduct();

    @POST("order/add-to-cart")
    Call<AppResource<OrderProductRespone>> addToCart(@Body OrderRequest orderRequest);

    @POST("order")
    Call<AppResource<OrderProductRespone>> fetchCart(@Body IdOrderRequest idOrderRequest);

    @POST("order/update")
    Call<AppResource<OrderProductRespone>> updateCart(@Body UpdateCartRequest updateCartRequest);

    @POST("order/confirm")
    Call<AppResource<String>> confirm(@Body ConfirmRequest confirmRequest);

}
