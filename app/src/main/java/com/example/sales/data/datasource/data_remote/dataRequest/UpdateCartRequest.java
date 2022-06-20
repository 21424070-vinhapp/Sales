package com.example.sales.data.datasource.data_remote.dataRequest;

import com.google.gson.annotations.SerializedName;

public class UpdateCartRequest {

    @SerializedName("id_product")
    private String idProduct;
    @SerializedName("id_order")
    private String idOrder;
    @SerializedName("quantity")
    private int quantity;

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public UpdateCartRequest(String idProduct, String idOrder, int quantity) {
        this.idProduct = idProduct;
        this.idOrder = idOrder;
        this.quantity = quantity;
    }
}
