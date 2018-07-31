package com.abhishek.staggeredgalleryview.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.abhishek.staggeredgalleryview.R;
import com.abhishek.staggeredgalleryview.databinding.ImageCardsBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<String> contentUri = new ArrayList<>();

    GalleryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageCardsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.image_cards,
                parent,
                false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.updateViews(contentUri.get(position));

    }

    public void setItems(ArrayList<String> contentUri) {
        this.contentUri = contentUri;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contentUri.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageCardsBinding binding;

        public ViewHolder(ImageCardsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }

        public void updateViews(String uri) {


            Glide.with(context).load(uri).into(binding.galleryImage);



        }
    }
}
