package com.example.sales.data.datasource.data_remote.dataResponse.product;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderProductRespone implements Serializable {

    @SerializedName("products")
    private List<ProductResponse> products;
    @SerializedName("id_user")
    private String idUser;
    @SerializedName("price")
    private int price;
    @SerializedName("status")
    private boolean status;
    @SerializedName("_id")
    private String id;


    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderProductRespone{" +
                "products=" + products +
                ", idUser='" + idUser + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", id='" + id + '\'' +
                '}';
    }
}
