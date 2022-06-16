package com.example.sales.data.datasource.remote;

import com.example.sales.data.datasource.remote.Request.UserRequest;
import com.example.sales.data.datasource.remote.Respone.UserRespone;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/sign-in")
    Call<AppResource<UserRespone>> SignIn(@Body UserRequest userRequest);

}
