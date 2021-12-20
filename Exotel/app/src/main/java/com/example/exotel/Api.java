package com.example.exotel;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface Api {

    @FormUrlEncoded
    @POST("{sid}/Calls/connect.json")
    Call<ResponseBody> getExotelResponse(
            @Header("Authorization") String authToken, @Path("sid") String sid,
            @Field("From") String from,
            @Field("To") String to
            //  @Field("CallerId") String callerId
    );


}
