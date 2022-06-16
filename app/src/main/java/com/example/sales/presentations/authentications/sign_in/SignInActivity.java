package com.example.sales.presentations.authentications.sign_in;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sales.data.datasource.remote.AppResource;
import com.example.sales.data.datasource.remote.Respone.UserRespone;
import com.example.sales.databinding.ActivitySignInBinding;
import com.example.sales.ultils.AppConstant;

public class SignInActivity extends AppCompatActivity {
    SignInViewModel signInViewModel;
    ActivitySignInBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        addViewModel();

        addEvent();

        addComformStatus();


        signInViewModel.performLogIn("demo100@gmail.com", "123456789");
    }

    private void addEvent() {

    }

    private void addViewModel() {
        signInViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignInViewModel(getApplicationContext());
            }
        }).get(SignInViewModel.class);
    }

    private void addComformStatus() {
        signInViewModel.getUserStatus().observe(this, new Observer<AppResource<UserRespone>>() {
            @Override
            public void onChanged(AppResource<UserRespone> userResponeAppResource) {
                switch (userResponeAppResource.status)
                {
                    case LOADING:
                        Log.d(AppConstant.TAG, "onChanged: LOADING" );
                        mBinding.layoutLoading.layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        Log.d(AppConstant.TAG, "onChanged: SUCCESS" );
                        mBinding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        break;
                    case ERROR:
                        Log.d(AppConstant.TAG, "onChanged: ERROR" + userResponeAppResource.message);
                        break;
                }
            }
        });
    }
}