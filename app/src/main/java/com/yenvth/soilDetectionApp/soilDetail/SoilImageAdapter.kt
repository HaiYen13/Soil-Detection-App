package com.yenvth.soilDetectionApp.soilDetail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yenvth.soilDetectionApp.R;

import java.util.ArrayList;

public class SoilImageAdapter extends RecyclerView.Adapter<SoilImageAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<String> mListImages;
    private OnSoilImageClickListener listener;

    public SoilImageAdapter(Context mContext, ArrayList<String> mListImages, OnSoilImageClickListener listener) {
        this.mContext = mContext;
        this.mListImages = mListImages;
        this.listener = listener;
    }

    public interface OnSoilImageClickListener {
        void onSoilImageClick(String url);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_image_show, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int pos) {
        if (!TextUtils.isEmpty(mListImages.get(pos))) {
            Glide.with(mContext).load(mListImages.get(pos)).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.image);
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onSoilImageClick(mListImages.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListImages.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private RoundedImageView image;
        private ProgressBar progressBar;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
