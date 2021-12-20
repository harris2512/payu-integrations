package com.example.runtimepermissionsexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView img_camera, img_read, img_location, img_call;
    TextView tv_allow_permission;


    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        img_camera = findViewById(R.id.img_camera);
        img_read = findViewById(R.id.img_read);
        img_location = findViewById(R.id.img_location);
        img_call = findViewById(R.id.img_call);
        tv_allow_permission = findViewById(R.id.tv_allow_permission);


        tv_allow_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!utils.hasPermissions(getApplicationContext(), PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Toast.makeText(getApplicationContext(), "Already Allowed All.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ALL) {

            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    results(grantResults);
                    return;
                }
            }
            results(grantResults);
            Toast.makeText(this, "Allowed.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Not Allowed.", Toast.LENGTH_SHORT).show();
        }


    }

    private void results(int[] grantResults) {
        final ProgressDialog progressDialog;
        Handler mHandler = new Handler();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 5000);


//        for (int grantResult : grantResults) {
//            if (grantResult == PackageManager.PERMISSION_GRANTED) {
//                imgChangeIcon(grantResult);
//            }
//        }


        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            img_camera.setImageResource(R.drawable.ic_tick_black);

        if (grantResults[1] == PackageManager.PERMISSION_GRANTED)
            img_read.setImageResource(R.drawable.ic_tick_black);

        if (grantResults[2] == PackageManager.PERMISSION_GRANTED)
            img_location.setImageResource(R.drawable.ic_tick_black);

        if (grantResults[3] == PackageManager.PERMISSION_GRANTED)
            img_call.setImageResource(R.drawable.ic_tick_black);

    }

    private void imgChangeIcon(int grantResult) {
        switch (grantResult) {
            case 0:
                img_camera.setImageResource(R.drawable.ic_tick_black);
                break;
            case 1:
                img_read.setImageResource(R.drawable.ic_tick_black);
                break;
            case 2:
                img_location.setImageResource(R.drawable.ic_tick_black);
                break;
            case 3:
                img_call.setImageResource(R.drawable.ic_tick_black);
                break;
        }
    }

}