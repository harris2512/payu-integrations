package com.example.retrofit_rxjava_mvp.java.ui.main;

import com.example.retrofit_rxjava_mvp.java.models.ClientServices;
import com.example.retrofit_rxjava_mvp.java.models.LoginStatusModel;
import com.example.retrofit_rxjava_mvp.java.models.ResponseModel;
import com.example.retrofit_rxjava_mvp.java.mvp.BasePresenter;
import com.example.retrofit_rxjava_mvp.java.mvp.BaseView;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;

public class MainContractor {


    public interface View extends BaseView {

        void handleResponse(List<ResponseModel> response);


        void setVendorList(LoginStatusModel response);

        void setImageDownload(InputStream byteStream);


        void setClientList(List<ClientServices> response);
    }

    public interface Presenter extends BasePresenter<View> {

        void getList(CompositeDisposable mCompositeDisposable);

        void getVendorList(String mAuthToken, Map<String, String> body);

        void getImageDownload(String mAuthToken, int imgId);

        void getClientList(String mAuthToken);
    }


}
