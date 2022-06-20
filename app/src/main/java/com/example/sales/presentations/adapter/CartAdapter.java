package com.example.sales.presentations.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sales.data.datasource.data_remote.dataResponse.product.ProductResponse;
import com.example.sales.databinding.ItemCartBinding;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<ProductResponse> lstProduct;

    private setOnclickCartItem setOnclickCartItem;

    public CartAdapter() {

    }

    public void updateCart(List<ProductResponse> listProduct) {
        if (this.lstProduct != null && this.lstProduct.size() > 0) {
            this.lstProduct.clear();
        }

        this.lstProduct = listProduct;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCartBinding binding = ItemCartBinding.inflate(layoutInflater, parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(lstProduct.get(position));
    }

    @Override
    public int getItemCount() {
        return lstProduct == null ? 0 : lstProduct.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding mBinding;

        public CartViewHolder(@NonNull ItemCartBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;

            mBinding.btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(setOnclickCartItem!=null)
                    {
                        setOnclickCartItem.onPlus(getAdapterPosition());
                        bind(lstProduct.get(getAdapterPosition()));
                    }
                }
            });

            mBinding.btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(setOnclickCartItem!=null)
                    {
                        setOnclickCartItem.onMinus(getAdapterPosition());
                        bind(lstProduct.get(getAdapterPosition()));
                    }
                }
            });

            mBinding.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(setOnclickCartItem!=null)
                    {
                        setOnclickCartItem.onDelete(getAdapterPosition());

                    }
                }
            });
        }

        void bind(ProductResponse productResponse)
        {
            mBinding.setProduct(productResponse);
        }


    }

    public void setOnListenerCartItem(setOnclickCartItem setOnclickCartItem)
    {
        this.setOnclickCartItem=setOnclickCartItem;
    }

    public interface setOnclickCartItem
    {
        void onPlus(int postion);
        void onMinus(int postion);
        void onDelete(int postion);
    }
}
