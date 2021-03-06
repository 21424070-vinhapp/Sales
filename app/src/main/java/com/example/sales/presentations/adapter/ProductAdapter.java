package com.example.sales.presentations.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sales.data.datasource.data_remote.dataResponse.product.ProductResponse;
import com.example.sales.databinding.ItemProductBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<ProductResponse> lstProduct;

    private OnItemClickProduct onItemClickProduct;

    public ProductAdapter(){
        lstProduct = new ArrayList<>();
    }

    public void updateListProduct(List<ProductResponse> data){
        if (lstProduct != null && lstProduct.size() > 0){
            lstProduct.clear();
        }
        lstProduct.addAll(data);
        notifyDataSetChanged();
    }

    public List<ProductResponse> getListFoods(){
        return lstProduct;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding itemProductBinding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductViewHolder(itemProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(lstProduct.get(position));
    }

    @Override
    public int getItemCount() {
        return lstProduct.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        ItemProductBinding mBinding;

        public ProductViewHolder(@NonNull ItemProductBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;

            mBinding.buttonAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickProduct != null) {
                        onItemClickProduct.onClick(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(ProductResponse product){
            mBinding.setProduct(product);
        }
    }

    public void setOnItemClickProduct(OnItemClickProduct OnItemClickProduct){
        this.onItemClickProduct = OnItemClickProduct;
    }

    public interface OnItemClickProduct {
        void onClick(int position);
    }

}
