package com.yenvth.soilDetectionApp.labeling;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.card.MaterialCardView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.LabelingModel;
import com.yenvth.soilDetectionApp.utils.DateUtils;

import java.util.ArrayList;

public class LabelingAdapter extends RecyclerView.Adapter<LabelingAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<LabelingModel> dataList;
    private OnLabelItemClickListener listener;

    public LabelingAdapter(Context mContext, ArrayList<LabelingModel> dataList, OnLabelItemClickListener listener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.listener = listener;
    }

    public interface OnLabelItemClickListener {
        void onLabelClickListener(LabelingModel labelingModel);

        void onDeleteLabel(String labelId);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_label, parent, false);
        return new MyHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int pos) {
        if (getItemViewType(pos) == 1) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#80cccccc"));
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));

        }

        LabelingModel labelingModel = dataList.get(pos);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onLabelClickListener(labelingModel);
            }
        });
        holder.tvName.setText(labelingModel.getLabelName());
        holder.tvDate.setText(DateUtils.convertTimes(labelingModel.getTimestamp(), "dd/MM/yyyy"));

        if (!TextUtils.isEmpty(labelingModel.getUrl())) {
            Glide.with(mContext).load(labelingModel.getUrl()).listener(new RequestListener<Drawable>() {
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

        holder.btnDelete.setOnClickListener(view -> {
            if (listener != null) {
                listener.onDeleteLabel(labelingModel.getLabelId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDate;
        private RoundedImageView image;
        private ProgressBar progressBar;
        private MaterialCardView cardView;
        private ImageView btnDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            image = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progressBar);
            cardView = itemView.findViewById(R.id.cardView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}


