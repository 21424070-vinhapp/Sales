package com.example.sales.presentations.authentications.sign_up;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sales.data.datasource.remote.AppResource;
import com.example.sales.data.datasource.remote.Request.UserRequest;
import com.example.sales.data.datasource.remote.Respone.UserRespone;
import com.example.sales.data.responsitories.AuthenticationRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    private AuthenticationRepository authenticationRepository;
    private MutableLiveData<AppResource<UserRespone>> userSignUp = new MutableLiveData<>();

    public SignUpViewModel(Context context) {
        authenticationRepository = new AuthenticationRepository(context);
    }

    public LiveData<AppResource<UserRespone>> getUserSignUp() {
        return userSignUp;
    }

    public void performSignUp(String email, String name, String password, String phone, String address) {
        userSignUp.setValue(new AppResource.Loading<>(null));
        Call<AppResource<UserRespone>> callUserSignUp = authenticationRepository.signUp(new UserRequest(email, name, password, phone, address));
        callUserSignUp.enqueue(new Callback<AppResource<UserRespone>>() {
            @Override
            public void onResponse(Call<AppResource<UserRespone>> call, Response<AppResource<UserRespone>> response) {
                if (response.isSuccessful()) {
                    AppResource<UserRespone> userData = response.body();
                    if (userData != null) {
                        userSignUp.setValue(new AppResource.Success<>(userData.data));
                    }
                } else {
                    try {
                        JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
                        String message = jsonObjectError.getString("message");
                        userSignUp.setValue(new AppResource.Error<>(message));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<UserRespone>> call, Throwable t) {
                userSignUp.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
