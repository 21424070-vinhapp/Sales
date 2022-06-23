package com.example.sales.data.datasource.data_remote.dataRequest;

import com.google.gson.annotations.SerializedName;

public class ConfirmRequest {

    @SerializedName("id_order")
    private String idOrder;
    @SerializedName("status")
    private boolean status;

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ConfirmRequest(String idOrder, boolean status) {
        this.idOrder = idOrder;
        this.status = status;
    }
}
