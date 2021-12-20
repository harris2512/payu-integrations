package com.example.retrofit_rxjava_mvp.java.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginStatusModel {

    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;

    //Login Auth Token
    @SerializedName("token")
    @Expose
    public String token;

    //vendor's Count
    @SerializedName("count")
    @Expose
    public Integer count;

    // Vendor List
    @SerializedName("VendorList")
    @Expose
    public List<VendorList> vendorList = null;

    //OTP verify
    @SerializedName("type")
    @Expose
    public String type;



}
