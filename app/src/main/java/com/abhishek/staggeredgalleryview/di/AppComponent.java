package com.abhishek.staggeredgalleryview.di;

import com.abhishek.staggeredgalleryview.SGApp;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        ActivityBuilder.class,
        AppModule.class})
public interface AppComponent extends AndroidInjector<SGApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(SGApp app);


        AppComponent build();
    }

    @Override
    void inject(SGApp instance);
}
