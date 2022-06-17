package com.example.sales.presentations.views.authentications.sign_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sales.R;
import com.example.sales.data.datasource.data_remote.AppResource;
import com.example.sales.data.datasource.data_remote.dataResponse.user.UserRespone;
import com.example.sales.databinding.ActivitySignUpBinding;
import com.example.sales.ultils.AppConstant;

public class SignUpActivity extends AppCompatActivity {
    SignUpViewModel mSignUpViewModel;
    ActivitySignUpBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        initView();

        addViewModel();

        addConformStatus();

        addEvent();
    }

    private void addEvent() {
        mBinding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String email, String name, String password, String phone, String address
                String email=mBinding.textEditEmail.getText().toString().trim();
                String name=mBinding.textEditName.getText().toString().trim();
                String password=mBinding.textEditPassword.getText().toString().trim();
                String phone=mBinding.textEditPhone.getText().toString().trim();
                String address=mBinding.textEditLocation.getText().toString().trim();

                if(email.isEmpty() || name.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty())
                {
                    Toast.makeText(SignUpActivity.this, "Không được để trông các ô", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mSignUpViewModel.performSignUp(email,name,password,phone,address);
                }
            }
        });
    }

    private void addConformStatus() {
        mSignUpViewModel.getUserSignUp().observe(this, new Observer<AppResource<UserRespone>>() {
            @Override
            public void onChanged(AppResource<UserRespone> userResponeAppResource) {
                switch ((userResponeAppResource.status)) {
                    case LOADING:
                        Log.d(AppConstant.TAG, "onChanged: Loading");
                        isLoading(true);
                        break;
                    case SUCCESS:
                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        isLoading(false);
                        finish();
                        break;
                    case ERROR:
                        Log.d(AppConstant.TAG, userResponeAppResource.message);
                        isLoading(false);
                        break;
                }
            }
        });
    }

    private void isLoading(boolean b) {
        if (b) {
            mBinding.containerLoading.layoutLoading.setVisibility(View.VISIBLE);
        } else {
            mBinding.containerLoading.layoutLoading.setVisibility(View.GONE);
        }
    }

    private void addViewModel() {
        mSignUpViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignUpViewModel(SignUpActivity.this);
            }
        }).get(SignUpViewModel.class);
    }

    private void initView() {
        setSupportActionBar(mBinding.toolbarRegister);
        getSupportActionBar().setTitle("Sign Up");
    }
}