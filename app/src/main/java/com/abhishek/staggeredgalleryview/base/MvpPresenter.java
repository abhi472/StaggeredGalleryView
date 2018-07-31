package com.abhishek.staggeredgalleryview.base;

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void onBackClick();

}

