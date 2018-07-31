package com.abhishek.staggeredgalleryview.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.abhishek.staggeredgalleryview.R;
import com.abhishek.staggeredgalleryview.databinding.ActivityListBinding;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ListActivity extends AppCompatActivity implements ListActivityMvpView {

    private static final int REQUEST_PERMISSION_KEY = 101;
    @Inject
    ListActivityPresenter<ListActivityMvpView> mPresenter;

    @Inject
    GalleryAdapter adapter;


    ActivityListBinding binding;

    CompositeDisposable disposable = new CompositeDisposable();

    private ArrayList<String> imageUris = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        mPresenter.onAttach(this);
        binding.setPresenter(mPresenter);
        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        } else {
            startImageFetch();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_KEY: {

                startImageFetch();
            }
            break;
        }
    }

    private void startImageFetch() {
        disposable.add(getUriObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> {
                    imageUris = strings;
                    adapter.setItems(imageUris);
                    setUpRecyclerView();
                    binding.list.setAdapter(adapter);


                }, throwable -> {
                    binding.bar.setVisibility(View.GONE);

                }));
    }

    private void setUpRecyclerView() {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                1);
        binding.list.setLayoutManager(manager); //
        binding.list.setHasFixedSize(true);     //These lines basically help the staggered layout manager to avoid
        binding.list.setItemViewCacheSize(20);  //slot machine behaviour
        binding.list.setDrawingCacheEnabled(true);//
        binding.list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);//
        binding.list.setVisibility(View.VISIBLE);
        binding.bar.setVisibility(View.GONE);

    }


    private Observable<ArrayList<String>> getUriObservable() {
        // we can alternatively delegate this task on a cursor loader but i am using RxJava2 for this
        return Observable.fromCallable(this::getImageUri).subscribeOn(Schedulers.io()); // creating an observable with fromCallable method it helps to
    }

    private ArrayList<String> getImageUri() {

        String path;
        ArrayList<String> images = new ArrayList<>();

        Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI; // fetching URIs for external and internal medias
        Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;


        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED};
        Cursor cursorExternal = getContentResolver().query(uriExternal,
                projection,
                null,
                null,
                null);
        Cursor cursorInternal = getContentResolver().query(uriInternal,
                projection,
                null,
                null,
                null);
        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal, cursorInternal}); // merging both cursors to get get a single cursor


        while (cursor.moveToNext()) {

            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            images.add(path);


        }
        if (cursorExternal != null)
            cursorExternal.close();
        if (cursorInternal != null)
            cursorInternal.close();
        cursor.close();
        return images;
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }


}
