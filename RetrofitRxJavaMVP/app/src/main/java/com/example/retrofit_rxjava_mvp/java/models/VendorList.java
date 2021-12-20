package com.example.retrofit_rxjava_mvp.java.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorList {

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
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("otp")
    @Expose
    public Integer otp;
    @SerializedName("type")
    @Expose
    public Integer type;
    @SerializedName("category")
    @Expose
    public Integer category;
    @SerializedName("latlon")
    @Expose
    public String latlon;
    @SerializedName("addressText")
    @Expose
    public String addressText;
    @SerializedName("locationText")
    @Expose
    public String locationText;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("applicationStatus")
    @Expose
    public Integer applicationStatus;
    @SerializedName("activationDate")
    @Expose
    public String activationDate;
    @SerializedName("aadharNumber")
    @Expose
    public String aadharNumber;
    @SerializedName("panNumber")
    @Expose
    public String panNumber;
    @SerializedName("GSTIN")
    @Expose
    public String gSTIN;
    @SerializedName("tanNumber")
    @Expose
    public String tanNumber;
    @SerializedName("transportationType")
    @Expose
    public Integer transportationType;
    @SerializedName("dailyCompensation")
    @Expose
    public Integer dailyCompensation;
    @SerializedName("monthlyNoOfDaysOff")
    @Expose
    public Integer monthlyNoOfDaysOff;
    @SerializedName("paymentFrequency")
    @Expose
    public String paymentFrequency;
    @SerializedName("isAllowTravelAllowance")
    @Expose
    public Boolean isAllowTravelAllowance;
    @SerializedName("allowanceType")
    @Expose
    public String allowanceType;
    @SerializedName("perDayAllowance")
    @Expose
    public Integer perDayAllowance;
    @SerializedName("isApplyKmCharges")
    @Expose
    public Boolean isApplyKmCharges;
    @SerializedName("ratePerKM")
    @Expose
    public Integer ratePerKM;
    @SerializedName("isPerformanceAllowance")
    @Expose
    public Boolean isPerformanceAllowance;
    @SerializedName("minOrderCount")
    @Expose
    public Integer minOrderCount;
    @SerializedName("incentivePerOrderBeyondMinCount")
    @Expose
    public Integer incentivePerOrderBeyondMinCount;
    @SerializedName("countOrdersOnlyWithinETA")
    @Expose
    public Boolean countOrdersOnlyWithinETA;
    @SerializedName("profilePic1")
    @Expose
    public String profilePic1;
    @SerializedName("profilePic2")
    @Expose
    public String profilePic2;
    @SerializedName("profilePic3")
    @Expose
    public String profilePic3;
    @SerializedName("mechanicStatus")
    @Expose
    public Integer mechanicStatus;
    @SerializedName("notes")
    @Expose
    public Object notes;
    @SerializedName("deviceId")
    @Expose
    public Object deviceId;
    @SerializedName("fcmToken")
    @Expose
    public Object fcmToken;
    @SerializedName("teamId")
    @Expose
    public Integer teamId;
    @SerializedName("shiftId")
    @Expose
    public Integer shiftId;
    @SerializedName("locationId")
    @Expose
    public Integer locationId;
    @SerializedName("addressId")
    @Expose
    public Object addressId;
    @SerializedName("isMinimumMonthlyGuarantee")
    @Expose
    public Boolean isMinimumMonthlyGuarantee = null;
    @SerializedName("minimumMonthlyGuarantee")
    @Expose
    public Integer minimumMonthlyGuarantee = null;
}
