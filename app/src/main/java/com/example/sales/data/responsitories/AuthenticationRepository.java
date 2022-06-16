package com.example.sales.data.responsitories;

import android.content.Context;

import com.example.sales.data.datasource.remote.ApiService;
import com.example.sales.data.datasource.remote.AppResource;
import com.example.sales.data.datasource.remote.Request.UserRequest;
import com.example.sales.data.datasource.remote.Respone.UserRespone;
import com.example.sales.data.datasource.remote.RetrofitClient;

import retrofit2.Call;

public class AuthenticationRepository {
    private ApiService apiService;

    public AuthenticationRepository(Context context)
    {
        apiService= RetrofitClient.getRetrofitClient(context).getApiService();
    }

    public Call<AppResource<UserRespone>> signIn(UserRequest userRequest){
        return apiService.SignIn(userRequest);
    }

    public Call<AppResource<UserRespone>> signUp(UserRequest userRequest)
    {
        return apiService.SignUp(userRequest);
    }
}
