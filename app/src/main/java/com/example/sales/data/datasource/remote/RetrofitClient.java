package com.example.sales.data.datasource.remote;

import com.example.sales.ultils.AppConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private Retrofit retrofit;
    private ApiService apiService;

    private RetrofitClient()
    {
        retrofit=createRetrofit();
        apiService=createApiService(retrofit);
    }

    private ApiService createApiService(Retrofit retrofit) {
        return  retrofit.create(ApiService.class);
    }

    public static RetrofitClient getRetrofitClient()
    {
        if(instance==null)
        {
            instance=new RetrofitClient();
        }
        return instance;
    }



    private Retrofit createRetrofit(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .connectTimeout(30,TimeUnit.SECONDS)
                .protocols(Arrays.asList(Protocol.HTTP_1_1))
                .build();

        Gson gson=new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public ApiService getApiService()
    {
        return apiService;
    }
}
