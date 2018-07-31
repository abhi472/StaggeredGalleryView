package com.abhishek.staggeredgalleryview;

import android.content.Context;

import com.abhishek.staggeredgalleryview.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class SGApp extends DaggerApplication {

    AndroidInjector<? extends DaggerApplication> androidInjector;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        androidInjector = DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return androidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
