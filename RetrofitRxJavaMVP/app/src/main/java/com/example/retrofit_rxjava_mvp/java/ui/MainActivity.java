package com.example.retrofit_rxjava_mvp.java.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit_rxjava_mvp.R;
import com.example.retrofit_rxjava_mvp.java.models.ClientServices;
import com.example.retrofit_rxjava_mvp.java.models.LoginStatusModel;
import com.example.retrofit_rxjava_mvp.java.models.ResponseModel;
import com.example.retrofit_rxjava_mvp.java.models.VendorList;
import com.example.retrofit_rxjava_mvp.java.ui.main.DataAdapter;
import com.example.retrofit_rxjava_mvp.java.ui.main.MainContractor;
import com.example.retrofit_rxjava_mvp.java.ui.main.MainPresenter;
import com.example.retrofit_rxjava_mvp.java.utils.RAUtils;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainContractor.View {

    private RecyclerView mRecyclerView;
    private DataAdapter mAdapter;
    private ArrayList<ResponseModel> mAndroidArrayList;
    MainPresenter mPresenter;
    String mAuthToken =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiS2FsYWl2YW5hbiBQIiwiaWQiOjEzLCJlbWFpbCI6IkthbGFpdmFuYW4ucEByZWFkeWFzc2lzdC5pbiIsIm1vYmlsZU5vIjoiOTc5MDc0MjcwMyIsIlJvbGVOYW1lIjoiQWRtaW4iLCJ2ZW5kb3JJZCI6bnVsbCwiaWF0IjoxNjAzNzkzODk1LCJleHAiOjE2MDYzODU4OTV9.UOw-dAFKSEMk_ucBOBPucTfDUQlMO40pz_iYyFZXg4E";
    protected List<VendorList> activeVendorList;
    protected List<ClientServices> clientServices;
    ImageView img_sampleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter();
        mPresenter.attachView(this);


        img_sampleImage = findViewById(R.id.img_sampleImage);

        initRecyclerView();

        //  loadJSON();

        loadVendorList();

        getImageDownload();

    }

    private void getImageDownload() {
       //  showPb();
        mPresenter.getImageDownload(mAuthToken, 1);
    }

    private void loadVendorList() {
        JSONObject filterObject = new JSONObject();
        JSONObject whereDataObject = new JSONObject();
        JSONArray includeDataArray = new JSONArray();
        JSONObject includeTeamObject = new JSONObject();
        JSONObject includeShiftObject = new JSONObject();

        try {
            whereDataObject.put("isActive", true);
            includeTeamObject.put("relation", "team");
            includeShiftObject.put("relation", "shift");
            // append it to your JSON array.
            includeDataArray.put(includeTeamObject);
            includeDataArray.put(includeShiftObject);

            filterObject.put("skip", 0);
            filterObject.put("limit", 10);
            filterObject.put("where", whereDataObject);
            filterObject.put("include", includeDataArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<String, String> data = new HashMap<>();
        data.put("filter", filterObject.toString());


        showPb();
        //mPresenter.getVendorList(mAuthToken, data);
        mPresenter.getClientList(mAuthToken);
    }


    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON() {
        showPb();
        // mPresenter.getList(mCompositeDisposable);
    }


    public void handleResponse(List<ResponseModel> response) {
        if (response != null) {
            mAndroidArrayList = new ArrayList<>(response);
            // mAdapter = new DataAdapter(mAndroidArrayList);
            //    mRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void setVendorList(LoginStatusModel response) {
        if (response.statusCode == 200) {
            activeVendorList = new ArrayList<>(response.vendorList);
         //   mAdapter = new DataAdapter(activeVendorList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void setImageDownload(InputStream byteStream) {
        Bitmap bm = BitmapFactory.decodeStream(byteStream);
        img_sampleImage.setImageBitmap(bm);
    }

    @Override
    public void setClientList(List<ClientServices> response) {
        clientServices = new ArrayList<>(response);
        mAdapter = new DataAdapter(clientServices);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void dismissPb() {
        RAUtils.dismissProgressDialog();
    }

    @Override
    public void showPb() {
        RAUtils.showProgressDialog(this, true);
    }

    @Override
    public void showToast(String message) {
//         Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onFailure(Object call, Object throwable, Object callback, Object additionalData) {
        if (throwable instanceof SocketTimeoutException) {
            //  showToast("Socket Time out. Please try again.");
            showRetryDialog(call, callback, getResources().getString(R.string.no_internet));
        } else if (throwable instanceof UnknownHostException) {
            // showToast("No Internet");
            showRetryDialog(call, callback, getResources().getString(R.string.no_internet));
        } else if (throwable instanceof ConnectException) {
            showRetryDialog(call, callback, getResources().getString(R.string.no_internet));
        } else {
            showToast(getResources().getString(R.string.api_no_response));
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        RAUtils.showRetryDialog(object1, object3, this, message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }


}