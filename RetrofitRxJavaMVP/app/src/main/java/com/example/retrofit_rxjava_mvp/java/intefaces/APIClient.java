package com.example.retrofit_rxjava_mvp.java.intefaces;

import com.example.retrofit_rxjava_mvp.java.models.ResponseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class APIClient {

    //    private static final String BASE_URL = "https://api.learn2crack.com/";
    private static final String BASE_URL = "http://dev.readyassist.net/";

    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder client;
    private static HttpLoggingInterceptor loggingInterceptor;
    private static String authenticationToken = null;


    public static Retrofit getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder();
        }


        client.interceptors().add(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder().header("Content-Type", "application/json");
                original.newBuilder().header("Accept", "application/json");

                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Gson gson = new GsonBuilder().setLenient().create();

        if (loggingInterceptor == null) {
            loggingInterceptor = new HttpLoggingInterceptor();
        }

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);

        OkHttpClient clients = client.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(clients)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }


    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void removeAuthenticationToken() {
        authenticationToken = null;
    }


}
