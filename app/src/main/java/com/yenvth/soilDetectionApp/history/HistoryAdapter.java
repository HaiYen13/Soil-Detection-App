package com.yenvth.soilDetectionApp.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.HistoryModel;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<HistoryModel> dataList;
    private OnHistoryItemClickListener listener;

    public HistoryAdapter(Context mContext, ArrayList<HistoryModel> dataList, OnHistoryItemClickListener listener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.listener = listener;
    }

    public interface OnHistoryItemClickListener {
        void onHistoryClickListener(HistoryModel historyModel);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_history, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int pos) {
        HistoryModel historyModel = dataList.get(pos);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onHistoryClickListener(historyModel);
            }
        });
        holder.tvName.setText(historyModel.getSoil_name());
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

