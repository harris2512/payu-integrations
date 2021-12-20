package com.example.exotel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class APIClient {

    private static final String BASE_URL = "https://api.exotel.com/v1/Accounts/";

//    private static final String BASE_URL = "https://" + BuildConfig.API_KEY + ":" + BuildConfig.API_TOKEN +
//            "@api.in.exotel.com/v1/Accounts/readyassist/";

    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder client;
    private static HttpLoggingInterceptor loggingInterceptor;

    private static String credentials;


    public static Retrofit getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder();
        }

        client.interceptors().add(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
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
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }


    public static String getBaseUrl() {
        return BASE_URL;
    }


}
