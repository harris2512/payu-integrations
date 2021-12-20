package com.example.retrofit_rxjava_mvp.java.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseModel {

    @SerializedName("var")
    @Expose
    public String var;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("api")
    @Expose
    public String api;

}
