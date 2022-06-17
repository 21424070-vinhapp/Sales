package com.example.sales.data.datasource.remote.Respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {

    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("price")
    private int price;
    @SerializedName("img")
    private String img;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("gallery")
    private List<String> gallery;
    @SerializedName("date_created")
    private String dateCreated;
    @SerializedName("date_updated")
    private Object dateUpdated;
    @SerializedName("__v")
    private int v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Object getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Object dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}
