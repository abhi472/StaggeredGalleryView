package com.abhishek.staggeredgalleryview.ui;

import android.databinding.ObservableField;

import com.abhishek.staggeredgalleryview.base.BasePresenter;

import javax.inject.Inject;

public class ListActivityPresenter <V extends ListActivityMvpView>
        extends BasePresenter<V>
        implements ListActivityMvpPresenter<V> {


    @Inject
    public ListActivityPresenter() {


    }

}
