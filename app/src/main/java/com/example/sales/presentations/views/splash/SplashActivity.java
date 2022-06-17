package com.example.sales.presentations.views.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.example.sales.databinding.ActivitySplashBinding;
import com.example.sales.presentations.views.authentications.sign_in.SignInActivity;
import com.example.sales.presentations.views.home.MainActivity;
import com.example.sales.ultils.SharePref;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                String token=SharePref.getInstance(SplashActivity.this).getToken();
                if(token.isEmpty())
                {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}