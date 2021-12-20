package com.example.exotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (!utils.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


        findViewById(R.id.btn_call)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callPhoneNumber();
                    }
                });


    }

    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                callExotel();
            } else {
                callExotel();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void callExotel() {

        String credentials = Credentials.basic(
                "d47fac7c7b832c393e5b4095a8f06d6e40e3e2efb7298df1",
                "99d356b2b70137bcecd98dab586b2e0b8185f8caffe911e3");

        String sid = "readyassist";

        try {
            APIClient.getClient().create(Api.class)
//                    .getExotelResponse("919500348308", "919585666690", "09513886363")
                    .getExotelResponse(credentials, sid, "919500348308", "919585666690")
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                            if (response.code() == 200 && response.body() != null) {
                                Toast.makeText(MainActivity.this,
                                        "Please wait..", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 400) {
                                Toast.makeText(MainActivity.this,
                                        "Invalid Call Parameters", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(MainActivity.this,
                                        String.valueOf(response.code()), Toast.LENGTH_SHORT).show();

                                Log.v("ResponseBody : ", String.valueOf(response.body()));
                            }


                        }

                        @Override
                        public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                            Toast.makeText(MainActivity.this,
                                    "Oops, We're unable to connect to server righ now. Please try again later.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception ae) {
            ae.printStackTrace();
        }


//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:" + "9500348308"));
//        startActivity(callIntent);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            }
        }

        if (requestCode == 1) {
            Toast.makeText(this, "allowed all", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "pls alloow", Toast.LENGTH_SHORT).show();
        }


    }


}