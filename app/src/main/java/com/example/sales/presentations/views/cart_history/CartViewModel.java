package com.example.sales.presentations.views.cart_history;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.dataRequest.ConfirmRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.IdOrderRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.OrderRequest;
import com.example.sales.data.datasource.data_remote.dataRequest.UpdateCartRequest;
import com.example.sales.data.datasource.data_remote.dataResponse.product.OrderProductRespone;
import com.example.sales.data.responsitories.CartRespository;
import com.example.sales.data.responsitories.OrderProductRespository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private MutableLiveData<AppResource<OrderProductRespone>> dataUpdate = new MutableLiveData<>();
    private MutableLiveData<AppResource<OrderProductRespone>> cart = new MutableLiveData<>();
    private MutableLiveData<AppResource<String>> confirm=new MutableLiveData<>();
    private CartRespository cartRespository;

    public CartViewModel(Context context) {
        cartRespository = new CartRespository(context);
    }

    public LiveData<AppResource<OrderProductRespone>> getCart() {
        return cart;
    }

    public LiveData<AppResource<OrderProductRespone>> getDataUpdate() {
        return dataUpdate;
    }

    public LiveData<AppResource<String>> getConfirm(){return confirm;}

    public void fetchCart(String idOrder) {
        cart.setValue(new AppResource.Loading<>(null));
        Call<AppResource<OrderProductRespone>> callListCart = cartRespository.fetchCart(new IdOrderRequest(idOrder));
        callListCart.enqueue(new Callback<AppResource<OrderProductRespone>>() {
            @Override
            public void onResponse(Call<AppResource<OrderProductRespone>> call, Response<AppResource<OrderProductRespone>> response) {
                if (response.isSuccessful()) {
                    AppResource<OrderProductRespone> data = response.body();
                    if (data != null) {
                        cart.setValue(new AppResource.Success<>(data.data));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        cart.setValue(new AppResource.Error<>(message));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<OrderProductRespone>> call, Throwable t) {
                cart.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

    public void updateCart(String idProduct, String idOrder, int Quantity) {
        dataUpdate.setValue(new AppResource.Loading<>(null));
        cartRespository.updateCart(new UpdateCartRequest(idProduct, idOrder, Quantity))
                .enqueue(new Callback<AppResource<OrderProductRespone>>() {
            @Override
            public void onResponse(Call<AppResource<OrderProductRespone>> call, Response<AppResource<OrderProductRespone>> response) {
                if (response.isSuccessful()) {
                    AppResource<OrderProductRespone> data = response.body();
                    if (data != null) {
                        dataUpdate.setValue(new AppResource.Success<>(data.data));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        dataUpdate.setValue(new AppResource.Error<>(message));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<OrderProductRespone>> call, Throwable t) {
                dataUpdate.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

    public void fetchConfirm(String id_order, boolean isConfirm)
    {
        cartRespository.confirm(new ConfirmRequest(id_order,isConfirm))
                .enqueue(new Callback<AppResource<String>>() {
                    @Override
                    public void onResponse(Call<AppResource<String>> call, Response<AppResource<String>> response) {
                        if (response.isSuccessful()) {
                            AppResource<String> data = response.body();
                            if (data != null) {
                                confirm.setValue(new AppResource.Success<>(data.data));
                            }
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                confirm.setValue(new AppResource.Error<>(message));
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AppResource<String>> call, Throwable t) {
                        confirm.setValue(new AppResource.Error<>(t.getMessage()));
                    }
                });
    }

}
