package com.example.sales.data.datasource.remote;

import com.example.sales.data.datasource.remote.Request.UserRequest;
import com.example.sales.data.datasource.remote.Respone.ProductResponse;
import com.example.sales.data.datasource.remote.Respone.UserRespone;

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

}
