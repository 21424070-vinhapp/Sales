package com.example.sales.presentations.views.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.dataRequest.IdOrderRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.OrderRequest;
import com.example.sales.data.datasource.data_remote.dataResponse.product.OrderProductRespone;
import com.example.sales.data.datasource.data_remote.dataResponse.product.ProductResponse;
import com.example.sales.data.responsitories.CartRespository;
import com.example.sales.data.responsitories.OrderProductRespository;
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

    private OrderProductRespository orderProductRespository;
    private MutableLiveData<AppResource<OrderProductRespone>> order = new MutableLiveData<>();

    private CartRespository cartRespository;
    private MutableLiveData<AppResource<OrderProductRespone>> cart=new MutableLiveData<>();

    public MainViewModel(Context context) {
        productRespository = new ProductRespository(context);
        orderProductRespository = new OrderProductRespository(context);
        cartRespository=new CartRespository(context);
    }

    public LiveData<AppResource<List<ProductResponse>>> getProduct() {
        return product;
    }

    public LiveData<AppResource<OrderProductRespone>> addToCart() {
        return order;
    }

    public LiveData<AppResource<OrderProductRespone>> getCart()
    {
        return cart;
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

    public void fetchOrder(String idProduct) {
        order.setValue(new AppResource.Loading<>(null));
        Call<AppResource<OrderProductRespone>> callOrder = orderProductRespository.addToCart(new OrderRequest(idProduct));
        callOrder.enqueue(new Callback<AppResource<OrderProductRespone>>() {
            @Override
            public void onResponse(Call<AppResource<OrderProductRespone>> call, Response<AppResource<OrderProductRespone>> response) {
                if (response.isSuccessful()) {
                    AppResource<OrderProductRespone> data = response.body();
                    if (data != null) {
                        order.setValue(new AppResource.Success<>(data.data));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        order.setValue(new AppResource.Error<>(message));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<OrderProductRespone>> call, Throwable t) {
                order.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

    public void fetchCart(String idOrder) {
        order.setValue(new AppResource.Loading<>(null));
        Call<AppResource<OrderProductRespone>> callOrder = cartRespository.fetchCart(new IdOrderRequest(idOrder));
        callOrder.enqueue(new Callback<AppResource<OrderProductRespone>>() {
            @Override
            public void onResponse(Call<AppResource<OrderProductRespone>> call, Response<AppResource<OrderProductRespone>> response) {
                if (response.isSuccessful()) {
                    AppResource<OrderProductRespone> data = response.body();
                    if (data != null) {
                        order.setValue(new AppResource.Success<>(data.data));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        order.setValue(new AppResource.Error<>(message));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<AppResource<OrderProductRespone>> call, Throwable t) {
                order.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

}
