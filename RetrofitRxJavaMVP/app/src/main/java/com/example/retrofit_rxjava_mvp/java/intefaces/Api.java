package com.example.retrofit_rxjava_mvp.java.intefaces;

import com.example.retrofit_rxjava_mvp.java.models.ClientServices;
import com.example.retrofit_rxjava_mvp.java.models.LoginStatusModel;
import com.example.retrofit_rxjava_mvp.java.models.ResponseModel;
import com.example.retrofit_rxjava_mvp.java.models.VendorList;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface Api {

    @GET("android/jsonarray/")
    Observable<List<ResponseModel>> register();

    //Get Vendors list for Search
    @Headers("Content-Type: application/json")
    @GET("api/mobile/vendorlist")
    Observable<LoginStatusModel> getVendorList(@Header("Authorization") String authToken,
                                               @QueryMap Map<String, String> options);

    //Download Image
    @Headers("Content-Type: application/json")
    @GET("api/files/download/{id}")
    Observable<ResponseBody> getDownloadImage(@Header("Authorization") String authToken,
                                              @Path("id") String id);


    @Headers("Content-Type: application/json")
    @GET("api/client-masters")
    Observable<List<ClientServices>> getClientList(@Header("Authorization") String authToken);



}
