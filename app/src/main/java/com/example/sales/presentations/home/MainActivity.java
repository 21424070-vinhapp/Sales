package com.example.sales.presentations.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.sales.R;
import com.example.sales.data.datasource.remote.AppResource;
import com.example.sales.data.datasource.remote.Respone.ProductResponse;
import com.example.sales.ultils.AppConstant;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel= new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MainViewModel(getApplicationContext());
            }
        }).get(MainViewModel.class);


        mainViewModel.fetchProduct();

        fetchProDuctStatus();
    }

    private void fetchProDuctStatus() {
        mainViewModel.getProduct().observe(this, new Observer<AppResource<List<ProductResponse>>>() {
            @Override
            public void onChanged(AppResource<List<ProductResponse>> listAppResource) {
                switch (listAppResource.status)
                {
                    case LOADING:
                        Log.d(AppConstant.TAG, "onChanged: LOADING"+listAppResource);
                        break;
                    case SUCCESS:
                        Log.d(AppConstant.TAG, "onChanged: SUCCESS");
                        break;
                    case ERROR:
                        Log.d(AppConstant.TAG, "onChanged: ERROR");
                        break;
                }
            }
        });
    }
}