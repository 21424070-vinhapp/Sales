package com.example.sales.data.datasource.remote.Request;

import com.google.gson.annotations.SerializedName;

public class UserRequest {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}