package com.example.retrofit_rxjava_mvp.java.ui.main;


import android.util.Log;
import android.widget.Toast;

import com.example.retrofit_rxjava_mvp.java.intefaces.APIClient;
import com.example.retrofit_rxjava_mvp.java.intefaces.Api;
import com.example.retrofit_rxjava_mvp.java.models.ClientServices;
import com.example.retrofit_rxjava_mvp.java.models.LoginStatusModel;
import com.example.retrofit_rxjava_mvp.java.models.ResponseModel;
import com.example.retrofit_rxjava_mvp.java.utils.RAUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class MainPresenter implements MainContractor.Presenter {

    private MainContractor.View mView;

    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(MainContractor.View view) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detach() {
        mView = null;
        mCompositeDisposable.clear();
    }


    @Override
    public void getList(CompositeDisposable nCompositeDisposable) {
        mCompositeDisposable.add(APIClient.getClient().create(Api.class).register()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        new Consumer<List<ResponseModel>>() {
                            @Override
                            public void accept(List<ResponseModel> response) throws Exception {
                                if (mView != null) {
                                    mView.dismissPb();
                                    mView.handleResponse(response);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable error) throws Exception {
                                if (mView != null) {
                                    mView.dismissPb();
                                    mView.onFailure(error, null, this, null);
                                }
                            }
                        }
                )
        );

    }

    //Vendor List
    @Override
    public void getVendorList(String mAuthToken, Map<String, String> body) {
        mCompositeDisposable.add(APIClient.getClient().create(Api.class)
                .getVendorList(mAuthToken, body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        new Consumer<LoginStatusModel>() {
                            @Override
                            public void accept(LoginStatusModel response) throws Exception {
                                if (mView != null) {
                                    mView.dismissPb();
                                    mView.setVendorList(response);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable error) throws Exception {
                                if (mView != null) {
                                    mView.dismissPb();
                                    mView.onFailure(error, null, this, null);
                                }
                            }
                        }
                )
        );
    }

    @Override
    public void getImageDownload(String mAuthToken, int imgId) {
        mCompositeDisposable.add(APIClient.getClient().create(Api.class)
                .getDownloadImage(mAuthToken, String.valueOf(imgId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody response) throws Exception {
                                if (mView != null) {
                                    mView.dismissPb();

                                    mView.setImageDownload(response.byteStream());

                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable error) throws Exception {
                                if (mView != null) {
                                    mView.dismissPb();

                                    HttpException errors = (HttpException) error;
                                    assert errors.response().errorBody() != null;
                                    JSONObject jObjError = new JSONObject(errors.response().errorBody().string());
                                    int code = Integer.parseInt(jObjError.getJSONObject("error").getString("statusCode"));
                                    if (code == 404) {
                                        mView.showToast("File not found.");
                                    }


                                }
                            }
                        }
                )

        );
    }



    //Client List
    @Override
    public void getClientList(String mAuthToken) {
        mCompositeDisposable.add(APIClient.getClient().create(Api.class)
                .getClientList(mAuthToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        new Consumer<List<ClientServices>>() {
                            @Override
                            public void accept(List<ClientServices> response) throws Exception {
                                if (mView != null) {
                                    mView.dismissPb();
                                    mView.setClientList(response);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable error) throws Exception {
                                if (mView != null) {
                                    mView.dismissPb();
                                    mView.onFailure(error, null, this, null);
                                }
                            }
                        }
                )
        );
    }


}
