package com.example.sales.presentations.views.authentications.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sales.R;
import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.dataResponse.user.UserRespone;
import com.example.sales.databinding.ActivitySignInBinding;
import com.example.sales.presentations.views.authentications.sign_up.SignUpActivity;
import com.example.sales.presentations.views.home.MainActivity;
import com.example.sales.ultils.AppConstant;
import com.example.sales.ultils.SharePref;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    SignInViewModel signInViewModel;
    ActivitySignInBinding mBinding;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //databiding
        mBinding = DataBindingUtil.setContentView(SignInActivity.this, R.layout.activity_sign_in);

        initView();

        addViewModel();

        addComformStatus();

        addEvent();


    }

    //set toolbar
    private void initView() {
        setSupportActionBar(mBinding.toolbarLogin);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Sign In");
    }

    private void addEvent() {
        mBinding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onClickSignIn();
            }
        });


        mBinding.textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });

    }

    //xu ly nut sign Up
    private void onClickSignUp() {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //Xu ly nut login
    private void onClickSignIn() {
        String email = mBinding.textEditEmail.getText().toString().trim();
        String pass = mBinding.textEditPassword.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Không được để trống email", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Không được để trống password", Toast.LENGTH_SHORT).show();
        } else {
            signInViewModel.performLogIn("demo100@gmail.com", "123456789");
        }
    }

    //tao ra view model
    private void addViewModel() {
        signInViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignInViewModel(getApplicationContext());
            }
        }).get(SignInViewModel.class);
    }

    //xu ly cac trang thai loading, success, error
    private void addComformStatus() {
        signInViewModel.getUserStatus().observe(this, new Observer<AppResource<UserRespone>>() {
            @Override
            public void onChanged(AppResource<UserRespone> userResponeAppResource) {
                switch (userResponeAppResource.status) {
                    case LOADING:
                        //Log.d(AppConstant.TAG, "onChanged: LOADING");
                        isShowLoading(true);
                        break;
                    case SUCCESS:
                        Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        SharePref.getInstance(SignInActivity.this).setToken(userResponeAppResource.data.getToken());
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        isShowLoading(false);
                        break;
                    case ERROR:
                        Log.d(AppConstant.TAG, "onChanged: ERROR" + userResponeAppResource.message);
                        isShowLoading(false);
                        break;
                }
            }
        });
    }

    //xu ly viec hien ra layout Loading
    private void isShowLoading(boolean isShow) {
        if (isShow) {
            mBinding.includeLoading.layoutLoading.setVisibility(View.VISIBLE);
        } else {
            mBinding.includeLoading.layoutLoading.setVisibility(View.GONE);
        }
    }
}