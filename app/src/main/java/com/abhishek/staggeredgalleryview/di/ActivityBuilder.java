package com.abhishek.staggeredgalleryview.di;

import com.abhishek.staggeredgalleryview.ui.ListActivity;
import com.abhishek.staggeredgalleryview.ui.ListActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = ListActivityModule.class)
    abstract ListActivity contributesSearchInjector();
}
