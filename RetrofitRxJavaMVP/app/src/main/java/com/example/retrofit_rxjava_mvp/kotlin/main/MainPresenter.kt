package com.example.retrofit_rxjava_mvp.kotlin.main

import com.example.retrofit_rxjava_mvp.java.intefaces.APIClient
import com.example.retrofit_rxjava_mvp.java.intefaces.Api
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class MainPresenter : MainContractor.Presenter {

    private var mView: MainContractor.View? = null
    lateinit var mCompositeDisposable: CompositeDisposable






    override fun attachView(view: MainContractor.View?) {
        mView = view
        mCompositeDisposable = CompositeDisposable()
    }

    override fun detach() {
        mView = null
        mCompositeDisposable.clear()
    }

    override fun getList(mCompositeDisposable: CompositeDisposable) {
        mCompositeDisposable.add(APIClient.getClient().create(Api::class.java).register()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (mView != null) {
                        mView!!.dismissPb()
                        mView!!.handleResponse(response)
                    }
                }
            ) { error ->
                if (mView != null) {
                    mView!!.dismissPb()
                    mView!!.onFailure(error, null, this, null)
                }
            }
        )
    }

    //Vendor List
    override fun getVendorList(mAuthToken: String?, body: Map<String?, String?>?) {
        mCompositeDisposable.add(APIClient.getClient().create(Api::class.java)
            .getVendorList(mAuthToken, body)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (mView != null) {
                        mView!!.dismissPb()
                        mView!!.setVendorList(response)
                    }
                }
            ) { error ->
                if (mView != null) {
                    mView!!.dismissPb()
                    mView!!.onFailure(error, null, this, null)
                }
            }
        )

    }

override fun getImageDownload(mAuthToken: String?, imgId: Int) {
        mCompositeDisposable.add(APIClient.getClient().create(Api::class.java)
            .getDownloadImage(mAuthToken, imgId.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (mView != null) {
                        mView!!.dismissPb()
                        mView!!.setImageDownload(response.byteStream())
                    }
                }
            ) { error ->
                if (mView != null) {
                    mView!!.dismissPb()
                    val errors = error as HttpException
                    val jObjError = JSONObject(errors.response().errorBody()!!.string())
                    val code = jObjError.getJSONObject("error").getString("statusCode").toInt()
                    if (code == 404) {
                        mView!!.showToast("File not found.")
                    }
                }
            }
        )
    }


}