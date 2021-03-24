package com.yenvth.soilDetectionApp.labeling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.LabelingModel;

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
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_label, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int pos) {
        LabelingModel labelingModel = dataList.get(pos);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onLabelClickListener(labelingModel);
            }
        });
        holder.tvName.setText(labelingModel.getLabel_name());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}


