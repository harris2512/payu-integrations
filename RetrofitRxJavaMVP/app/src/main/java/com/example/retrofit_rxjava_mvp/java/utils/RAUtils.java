package com.example.retrofit_rxjava_mvp.java.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


import com.example.retrofit_rxjava_mvp.R;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

/*
 * Created by Harris on 12/2/2020.
 */

public class RAUtils {

    public static Dialog progressDialog;
    private static AlertDialog alertDialog;

    //SessionManager sessionManager;

    //ProgressDialogue
    public static void showProgressDialog(Context context, boolean isShow) {
        if (!((Activity) context).isFinishing()) {
            progressDialog = new Dialog(context);
            progressDialog.setContentView(R.layout.dialog_progressbar);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            progressDialog.setCancelable(true);
            if (isShow) {
                progressDialog.show();
            }

        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }



    //RetryAlert
    public static void showRetryDialog(final Object object1, final Object object3, Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton(context.getString(R.string.txt_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            ((Call<?>) object1).clone().enqueue((Callback) object3);
                            alertDialog.dismiss();
                        }
                    });

            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);

        }
    }


    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    public static void showAlertDialog(Context context, String title, String message) {
        if (!((Activity) context).isFinishing()) {
//            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton(context.getString(R.string.txt_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            alertDialog.dismiss();
                        }
                    });

            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);

        }
    }

    public static void showDialogForAlert(Context context, String title, String message) {
        //then we will inflate the custom alert dialog xml that we created
        @SuppressLint("InflateParams") final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_success_error, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setCancelable(false);

        TextView tv_dialog_title = dialogView.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_message = dialogView.findViewById(R.id.tv_dialog_message);
        TextView dialog_ok_button = dialogView.findViewById(R.id.dialog_ok_button);
        tv_dialog_title.setText(title);
        tv_dialog_message.setText(message);
        dialog_ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();
        alertDialog.show();

    }


    public static int logError(Throwable error) {
        HttpException errors = (HttpException)error;
        String errorBody = null;
        int code = 0;
        try {
            assert errors.response().errorBody() != null;
            errorBody = errors.response().errorBody().string();
            JSONObject jObjError = new JSONObject(errors.response().errorBody().string());
            code = Integer.parseInt(jObjError.getJSONObject("error").getString("statusCode"));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return code;
    }


}
