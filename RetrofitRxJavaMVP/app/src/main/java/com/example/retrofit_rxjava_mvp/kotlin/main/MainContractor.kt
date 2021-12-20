package com.example.retrofit_rxjava_mvp.kotlin.main

import com.example.retrofit_rxjava_mvp.java.models.LoginStatusModel
import com.example.retrofit_rxjava_mvp.java.models.ResponseModel
import com.example.retrofit_rxjava_mvp.java.mvp.BasePresenter
import com.example.retrofit_rxjava_mvp.java.mvp.BaseView
import io.reactivex.disposables.CompositeDisposable
import java.io.InputStream

class MainContractor {

    interface View : BaseView {
        fun handleResponse(response: List<ResponseModel?>?)
        fun setVendorList(response: LoginStatusModel?)
        fun setImageDownload(byteStream: InputStream?)
    }

    interface Presenter : BasePresenter<View?> {
        fun getList(mCompositeDisposable: CompositeDisposable)
        fun getVendorList(mAuthToken: String?, body: Map<String?, String?>?)
        fun getImageDownload(mAuthToken: String?, imgId: Int)
    }

}