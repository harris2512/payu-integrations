package com.example.retrofit_rxjava_mvp.java.mvp;

/**
 * Created by Harris on 12/2/2020.
 */

public interface BasePresenter<T extends BaseView> {

  void attachView(T view);

  void detach();
}
