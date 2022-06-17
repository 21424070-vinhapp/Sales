package com.example.sales.ultils;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sales.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AppBinding {
    @BindingAdapter("price")
    public static void setPrice(TextView textView, int price) {
        NumberFormat formatter = new DecimalFormat("#,###");
        textView.setText("Giá " + formatter.format(price) + " VNĐ");
    }

    @BindingAdapter("img")
    public static void setImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(AppConstant.BASE_URL + url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }
}
