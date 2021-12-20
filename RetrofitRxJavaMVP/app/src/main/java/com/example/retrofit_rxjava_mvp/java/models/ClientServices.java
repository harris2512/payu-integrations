package com.example.retrofit_rxjava_mvp.java.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientServices {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;
    @SerializedName("isActive")
    @Expose
    public Boolean isActive;
    @SerializedName("name")
    @Expose
    public String name;

}
