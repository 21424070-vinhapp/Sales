package com.example.sales.presentations.views.authentications.sign_in;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.dataRequest.UserRequest;
import com.example.sales.data.datasource.data_remote.dataResponse.user.UserRespone;
import com.example.sales.data.responsitories.AuthenticationRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInViewModel extends ViewModel {
    private AuthenticationRepository authenticationRepository;
    private MutableLiveData<AppResource<UserRespone>> userLogIn = new MutableLiveData<>();

    public SignInViewModel(Context context) {
        authenticationRepository = new AuthenticationRepository(context);
    }

    public LiveData<AppResource<UserRespone>> getUserStatus() {
        return userLogIn;
    }

    public void performLogIn(String email, String pass) {
        userLogIn.setValue(new AppResource.Loading(null));
        Call<AppResource<UserRespone>> callLogIn = authenticationRepository.signIn(new UserRequest(email, pass));
        callLogIn.enqueue(new Callback<AppResource<UserRespone>>() {
            @Override
            public void onResponse(Call<AppResource<UserRespone>> call, Response<AppResource<UserRespone>> response) {
                if (response.isSuccessful()) {
                    AppResource<UserRespone> userRespone = response.body();
                    if (userRespone.data != null) {
                        userLogIn.setValue(new AppResource.Success<>(userRespone.data));
                    }
                } else {
                    try {
                        JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
                        String message = jsonObjectError.getString("message");
                        userLogIn.setValue(new AppResource.Error<>(message));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<AppResource<UserRespone>> call, Throwable t) {
                userLogIn.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
