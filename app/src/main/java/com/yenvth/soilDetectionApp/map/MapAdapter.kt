package com.yenvth.soilDetectionApp.map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.SoilModel;
import com.yenvth.soilDetectionApp.utils.Constant;

import java.util.ArrayList;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<SoilModel> dataList;
    private OnSoilMapItemClickListener listener;

    public MapAdapter(Context mContext, ArrayList<SoilModel> dataList, OnSoilMapItemClickListener listener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.listener = listener;
    }

    public interface OnSoilMapItemClickListener {
        void onSoilMapClickListener(SoilModel soilModel);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_soil_map, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int pos) {
        SoilModel soilModel = dataList.get(pos);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onSoilMapClickListener(soilModel);
            }
        });
        holder.lnImage.setBackgroundColor(mContext.getResources().getColor(Constant.getColorArea(soilModel.getSoilCode().toLowerCase())));
        holder.tvName.setText(soilModel.getNameVi());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private LinearLayout lnImage;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            lnImage = itemView.findViewById(R.id.lnImage);
        }
    }
}

