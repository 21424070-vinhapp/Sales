package com.example.sales.data.datasource.data_remote.dataRequest;

import com.google.gson.annotations.SerializedName;

public class IdOrderRequest {


    @SerializedName("id_order")
    private String idOrder;

    public String getIdOrder() {
        return idOrder;
    }
    public IdOrderRequest(String idOrder) {
        this.idOrder = idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }


}
