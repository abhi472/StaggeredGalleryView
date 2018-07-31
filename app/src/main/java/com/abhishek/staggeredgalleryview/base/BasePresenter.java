package com.abhishek.staggeredgalleryview.base;

import javax.inject.Inject;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {


    private V mvpView;


    @Inject
    public BasePresenter() {

    }


    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;

    }

    @Override
    public void onDetach() {
        this.mvpView = null;
    }

    @Override
    public void onBackClick() {

    }


    public V getMvpView() {
        return mvpView;
    }
}
