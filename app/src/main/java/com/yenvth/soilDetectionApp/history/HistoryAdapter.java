package com.yenvth.soilDetectionApp.history;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.HistoryModel;
import com.yenvth.soilDetectionApp.utils.DateUtils;

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
        void onHistoryClick(HistoryModel historyModel);

        void onItemDelete(String historiesId);
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_history, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int pos) {
        if (getItemViewType(pos) == 1) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#80cccccc"));
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));

        }
        HistoryModel historyModel = dataList.get(pos);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onHistoryClick(historyModel);
            }
        });

        holder.btnDelete.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemDelete(historyModel.getHistoriesId());
            }
        });
        holder.tvName.setText(historyModel.getSoilName());
        holder.tvDate.setText(DateUtils.convertTimes(historyModel.getTimestamp(), "dd/MM/yyyy HH:mm"));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView btnDelete;
        private TextView tvDate;
        private MaterialCardView cardView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvName = itemView.findViewById(R.id.tvName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}

