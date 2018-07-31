package com.abhishek.staggeredgalleryview.ui;

import com.abhishek.staggeredgalleryview.di.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ListActivityModule {

    @Provides
    @PerActivity
    ListActivityMvpPresenter<ListActivityMvpView> provideListActivityPresenter(
            ListActivityPresenter<ListActivityMvpView> presenter) {
        return presenter;
    }

    @Provides
    GalleryAdapter provideAdapter(ListActivity activity) {
        return new GalleryAdapter(activity);
    }

}
