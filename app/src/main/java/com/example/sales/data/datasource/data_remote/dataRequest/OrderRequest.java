package com.example.sales.data.datasource.data_remote.dataRequest;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class OrderRequest {

    @SerializedName("id_product")
    private String idProduct;

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public OrderRequest(String idProduct) {
        this.idProduct = idProduct;
    }
}
